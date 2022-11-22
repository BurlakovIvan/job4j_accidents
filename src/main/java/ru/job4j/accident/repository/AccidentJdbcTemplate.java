package ru.job4j.accident.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final static String ADD_ACCIDENT = """
                                               INSERT INTO accidents (name, text, address, type_id)
                                               VALUES (?, ?, ?, ?)
                                               """;
    private final static String ADD_RULES_ACCIDENT = """
                                               INSERT INTO rulesAccidents (accident_id, rule_id)
                                               VALUES (?, ?)
                                               """;

    private final static String FIND_ALL_ACCIDENT = """
                                 SELECT a.id as id, a.name as name, a.text as text, a.address as address,
                                 t.id as type_id, t.name as type_name
                                 FROM accidents a
                                 LEFT JOIN typeAccidents t
                                 ON a.type_id = t.id
                                 ORDER BY a.id
                                 """;

    private final static String FIND_ACCIDENT = """
                                  SELECT a.id as id, a.name as name, a.text as text, a.address as address,
                                  t.id as type_id, t.name as type_name
                                  FROM accidents a LEFT JOIN typeAccidents t
                                  ON a.type_id = t.id WHERE a.id = ?
                                  ORDER BY a.id
                                  """;

    private final static String FIND_RULE_WITH_WHERE = """
                                  SELECT ra.rule_id as id, rt.name as name
                                  FROM rulesAccidents ra
                                  INNER JOIN rulesTable rt
                                  ON ra.rule_id = rt.id
                                  WHERE %s
                                  ORDER BY ra.rule_id
                                  """;

    private final static String SELECT_RULES = "SELECT id, name FROM rulesTable ORDER BY id";

    private final static String SELECT_ACCIDENT_TYPE = "SELECT id, name FROM typeAccidents";

    private final static String UPDATE_ACCIDENT = """
                                                  UPDATE accidents
                                                  SET name = ?, text = ?, address = ?,
                                                  type_id = ? WHERE id = ?
                                                  """;

    private final static String DELETE_RULE_ACCIDENT = "DELETE FROM rulesAccidents WHERE accident_id = ?";

    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(ADD_ACCIDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps; }, keyHolder);
        int accidentId = (Integer) keyHolder.getKeys().get("id");
        for (Rule rule : accident.getRules()) {
            jdbc.update(ADD_RULES_ACCIDENT,
                    accidentId, rule.getId());
        }
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query(FIND_ALL_ACCIDENT,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(newAccidentType(rs.getInt("type_id"),
                            rs.getString("type_name")));
                    accident.setRules(Set.copyOf(findRulesByAccidentId(rs.getInt("id"))));
                    return accident;
                });
    }

    public List<AccidentType> findAllAccidentType() {
        return jdbc.query(SELECT_ACCIDENT_TYPE,
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public List<Rule> findAllRule() {
        return jdbc.query(SELECT_RULES,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Set<Rule> findRule(List<Integer> rulesList) {
        Set<Rule> result = new HashSet<>();
        for (int rl : rulesList) {
            result.addAll(Set.copyOf(findRulesById(rl)));
        }
        return result;
    }

    public void update(Accident accident) {
        jdbc.update(DELETE_RULE_ACCIDENT, accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update(ADD_RULES_ACCIDENT, accident.getId(), rule.getId());
        }
        jdbc.update(UPDATE_ACCIDENT,
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
    }

    public Optional<Accident> findById(int accidentId) {
        Accident rsl = jdbc.queryForObject(FIND_ACCIDENT,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(newAccidentType(rs.getInt("type_id"),
                            rs.getString("type_name")));
                    accident.setRules(Set.copyOf(findRulesByAccidentId(accident.getId())));
                    return accident;
                }, accidentId);
        return Optional.ofNullable(rsl);
    }

    public Optional<AccidentType> findByAccidentTypeId(int id) {
        AccidentType rsl = jdbc.queryForObject(String.format("%s WHERE id = ?", SELECT_ACCIDENT_TYPE),
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                }, id);
        return Optional.ofNullable(rsl);
    }

    private List<Rule> findRulesByAccidentId(int accidentId) {
        return findRule(accidentId, "ra.accident_id = ?");
    }

    private List<Rule> findRulesById(int id) {
        return findRule(id, "ra.rule_id = ?");
    }

    private List<Rule> findRule(int id, String where) {
        return jdbc.query(String.format(FIND_RULE_WITH_WHERE, where),
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, id);
    }

    private AccidentType newAccidentType(int id, String name) {
        return new AccidentType(id, name);
    }
}
