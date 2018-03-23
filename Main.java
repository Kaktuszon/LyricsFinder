import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;

public class Main  {
    private javax.swing.JPanel JPanel;
    private JTextField tfSong;
    private JLabel jlAppName;
    private JLabel jlArtist;
    private JLabel jlSong;
    private JButton jbSearch;
    private JTextField tfArtist;
    private JTextArea taLyrics;
    private JScrollPane scroll;
    private JButton saveButton;
    private String loadingURL = "";

    public Main() {
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //Remove horizontal scroll
        taLyrics.setEditable(false); //Can't edit textArea
        jbSearch.addActionListener(new ActionListener() { //Button click
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });
        tfSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        //If clicked, save lyrics into a .txt file
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Song tempSong = new Song(tfArtist.getText(), tfSong.getText(), loadPage(loadingURL));
                String tempName = tempSong.getArtist() + "_" + tempSong.getSong() + ".txt";
                try(PrintWriter out = new PrintWriter(tempName)) {
                    out.println(tempSong.getLyrics());
                } catch (java.io.FileNotFoundException exp) {
                    System.out.println(exp);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public String loadPage(String url) {
        try {
            String html = url; //Set the html
            //Connect to webpage
            Document doc = Jsoup.connect(html).header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000).get();
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false)); //Load the webpage correctly
            doc.select("br").append("\\n"); //Change br (html) to newline
            doc.select("p").append("\\n\\n"); //Change p (html) to 2xnewline

            Elements ps = doc.select("div:not([class])"); //Select correct div for lyrics on AZLyrics

            String text = ps.text().replaceAll("\\\\n", "\n"); //replace to correct newline
            return text; //return all text
        } catch (IOException e) {
               System.out.println(e);
        }

        return "";
    }

    public void loadData() {
        loadingURL = "http://www.azlyrics.com/lyrics/" + tfArtist.getText().toLowerCase().replaceAll("\\s", "") + "/" + tfSong.getText().toLowerCase().replaceAll("\\s", "") + ".html"; //Load URL
        System.out.println(loadingURL); //Print URL for debugging
        taLyrics.setText(loadPage(loadingURL)); //Set the text from function loadPage
    }
}