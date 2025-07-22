package game.two048.rule;

import game.two048.model.Board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuleEngine {
    private final List<Rule> rules = new ArrayList<>();

    public RuleEngine() {
        initializeRules();
    }
    private void initializeRules() {
        // Add rules in any order - they'll be sorted by priority
        rules.add(new NoMoreMoveRule());
        rules.add(new Two048Rule());
    }
    public List<Rule> evaluate(Board board) {
        List<Rule> appliedRules = new ArrayList<>();

        //sort the rules by order
        rules.sort(Comparator.comparingInt(Rule::getOrder));
        for (Rule rule : rules) {
            if (rule.evaluate(board)) {
                appliedRules.add(rule);

            }
        }
        return appliedRules;
    }
}
