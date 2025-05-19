import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EgyptQuizGameGUI extends JFrame {
    private int currentQuestion = 0;
    private int score = 0;
    private ArrayList<Question> questions;

    // GUI components
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[3];
    private JButton hintButton;
    private JLabel scoreLabel;

    public EgyptQuizGameGUI() {
        setTitle("🏺 ＥＧＹＰＴの秘宝クイズ");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //font 

        Font font = new Font("Serif", Font.BOLD, 20);
        getContentPane().setBackground(new Color(220, 237, 255));


        questions = Question.getShuffledQuestions();


        questionLabel = new JLabel("クイズ読み込み中...", SwingConstants.CENTER);
        questionLabel.setFont(font);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(225, 220, 255));

        for (int i = 0; i < 3; i++) {
            int idx = i;
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(font);
            optionButtons[i].addActionListener(e -> checkAnswer(idx));
            buttonPanel.add(optionButtons[i]);
        }

        hintButton = new JButton("💡 ヒントを見る");
        hintButton.setFont(font);
        hintButton.addActionListener(e -> showHint());
        buttonPanel.add(hintButton);

        scoreLabel = new JLabel("宝ポイント: 0", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        add(questionLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        showNextQuestion();
        setVisible(true);
    }

    private void showHint() {
        JOptionPane.showMessageDialog(this,
                questions.get(currentQuestion).hint,
                "ヒント",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkAnswer(int selectedIndex) {
        Question q = questions.get(currentQuestion);
        if (selectedIndex == q.correctIndex) {
            score += 10;
            JOptionPane.showMessageDialog(this, "✅ 正解！宝ポイント +10", "正解", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "❌ 不正解…", "不正解", JOptionPane.ERROR_MESSAGE);
        }
        currentQuestion++;
        if (currentQuestion < questions.size()) {
            showNextQuestion();
        } else {
            endGame();
        }
    }


    private void showNextQuestion() {
        Question q = questions.get(currentQuestion);
        questionLabel.setText("<html><div style='text-align: center;'>" + q.question + "</div></html>");
        for (int i = 0; i < 3; i++) {
            optionButtons[i].setText(q.options[i]);
        }
        scoreLabel.setText("宝ポイント: " + score);
    }

    private void endGame() {
        String message;
        if (score == questions.size() * 10) {
            message = "🎉 全問正解！ファラオの宝を手に入れた！";
        } else if (score >= 20) {
            message = "✨ よく頑張った！貴重な宝を発見した！";
        } else {
            message = "😢 宝はまだ遠い…また挑戦しよう！";
        }
        JOptionPane.showMessageDialog(this, message + "\nスコア: " + score, "冒険終了", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }


    static class Question {
        String question;
        String[] options;
        int correctIndex;
        String hint;

        public Question(String question, String[] options, int correctIndex, String hint) {
            this.question = question;
            this.options = options;
            this.correctIndex = correctIndex;
            this.hint = hint;
        }

        public static ArrayList<Question> getShuffledQuestions() {
            ArrayList<Question> list = new ArrayList<>();
            list.add(new Question("太陽神として崇拝された神は？",
                    new String[]{"アヌビス", "ホルス", "セト"}, 1, "目のシンボルで有名"));
            list.add(new Question("ナイル川はどちらに流れる？",
                    new String[]{"南から北", "北から南", "東から西"}, 0, "上流は南にある"));
            list.add(new Question("ファラオの墓として有名な建築物は？",
                    new String[]{"神殿", "スフィンクス", "ピラミッド"}, 2, "三角形の石の構造"));
            list.add(new Question("死後の世界を司る神は？",
                    new String[]{"ラー", "オシリス", "バステト"}, 1, "緑の肌を持つ神"));
            list.add(new Question("エジプトで使われた紙の元は？",
                    new String[]{"パピルス", "絹", "羊皮紙"}, 0, "植物から作る"));
            Collections.shuffle(list);
            return list;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EgyptQuizGameGUI::new);
    }
}
