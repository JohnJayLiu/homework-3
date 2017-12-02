package javaPro;
import data.*;
import opennlp.maxent.ModelApplier;
import Hander.*;
import segmenter.*;
import tf_idf.*;
import worldCloud.WordCloudBuilder;
import sorter.*;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.html.*;
import javax.xml.soap.Text;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.apache.log4j.varia.StringMatchFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import recommand.*;
import resultTextColor.ResultSetingImpl;
import com.github.davidmoten.guavamini.Lists;
import com.github.davidmoten.rtree.internal.util.Pair;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;


public class SimpleFrame {
	public static void main(String[] args) {
		Frame mainFrame=new Frame();
		SearchField searchField=new SearchField();
		searchField.setBounds(10, 50, 500, 500);
		mainFrame.add(searchField);
	}
}
class Frame extends JFrame{
	public Frame() {
		// TODO Auto-generated constructor stub
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension screemSize=toolkit.getScreenSize();
		int screenWidth=screemSize.width;
		int screenHeight=screemSize.height;
		this.setLayout(null);
		this.setSize(screenWidth/4, screenHeight/2);
		this.setVisible(true);
	}
}
class ImportButton extends JButton{
	String filePath;
	public ImportButton() {
		// TODO Auto-generated constructor stub
		this.setText("Import");
		filePath=null;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();  
				jf.showDialog(null,null);  
				File fi = jf.getSelectedFile();  
				filePath = fi.getAbsolutePath();
			}
		});
	}
	public String getFilePath() {
		return filePath;
	}
}
class SearchField extends JPanel{
	JButton searchButton;
	JTextField jTextField;
	ImportButton importButton;
	public SearchField() {
		// TODO Auto-generated constructor stub
		searchButton=new JButton("Search");
		jTextField=new JTextField();
		importButton=new ImportButton();
		final JPanel resultField=new JPanel();
		resultField.setLayout(null);
		this.setLayout(null);
		resultField.setBounds(0, 70, 1200, 500);
		importButton.setBounds(0, 0, 100, 30);
		jTextField.setBounds(0, 35, 200, 30);
		searchButton.setBounds(210, 35, 100, 30);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChineseSegmenterImpl chineseSegmenterImpl=new ChineseSegmenterImpl();
				FileHander fileHander=new FileHander();
				String string=jTextField.getText();
				RecommandImpl recommandImpl=new RecommandImpl();
				SorterImpl sorterImpl=new SorterImpl();
				ResultSetingImpl resultSetingImpl=new ResultSetingImpl();
				Stockinfo[] stockinfo;
				List<String> wordList=new LinkedList<>();
				try {
					wordList=chineseSegmenterImpl.getWordsFromString(string);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					stockinfo = fileHander.getStockInfoFromFile(importButton.getFilePath());
					int []recommanded=new int [10];
					recommanded=
							sorterImpl.matrixSorter(recommandImpl.calculateMatrix(stockinfo, jTextField.getText()));
					for(int i=0;i<10;i++) {
						String result=
								stockinfo[(recommanded[i])].getTitle()+stockinfo[(recommanded[i])].getContent()+stockinfo[(recommanded[i])].getAnswer();
						SearchResult searchResult=new SearchResult();
						searchResult.setString(result,stockinfo);
						searchResult.setText(resultSetingImpl.setResultColor(result, wordList));
						searchResult.setVerticalAlignment(SwingConstants.TOP);
						searchResult.setBounds(0, 30+i*30, 400, 30);
						resultField.add(searchResult);
						} 
					}catch (Exception e2) {
					// TODO Auto-generated catch block
						e2.printStackTrace();
						}
				}
			});
		this.add(resultField);
		this.add(importButton);
		this.add(searchButton);
		this.add(jTextField);
	}
}
class SearchResult extends JLabel{
	String string;
	Stockinfo []stockinfos;
	public SearchResult() {
		string =null;
		// TODO Auto-generated constructor stub
		this.addMouseListener(new MyMouseListener() {
			@Override
			public void mouseDoubleClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				ResultFrame resultFrame=new ResultFrame(string,stockinfos);
			}
			@Override
			public void mouseSingleClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void setString (String string ,Stockinfo[]stockinfos) {
		this.string=string;
		this.stockinfos=stockinfos;
	}
}
//鼠标双击
abstract class MyMouseListener extends java.awt.event.MouseAdapter {
	private static boolean flag=false;
	private static int clickNum=0;
	public void mouseClicked(MouseEvent e) 
	{
		final MouseEvent me=e;
		this.flag=false;
		if(this.clickNum == 1) 
		{
			this.mouseDoubleClicked(me);
			this.clickNum=0;
			this.flag=true;
			return;
			}
		java.util.Timer timer=new java.util.Timer();
		timer.schedule(new java.util.TimerTask()
		{
			private int n=0;
			public void  run() {
				if(MyMouseListener.flag){
					MyMouseListener.clickNum=0;
					this.cancel();
					return;
					}
				if(n == 1) {
					mouseSingleClicked(me);
					MyMouseListener.flag=true;
					MyMouseListener.clickNum=0;
					n=0;
					this.cancel();
					return;
					}
				clickNum++;
				n++;
				}
			},new java.util.Date(),500);
		}
public abstract void mouseSingleClicked(MouseEvent e);
public abstract void mouseDoubleClicked(MouseEvent e);
}
class ResultFrame extends JFrame{
	JTextArea textArea;
	String string;
	Stockinfo[]stockinfos;
	public ResultFrame(String str,Stockinfo[]stockinfos) {
		textArea=new JTextArea(str);
		System.out.println(str);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Serif",Font.BOLD , 20));
		JScrollPane jScrollPane=new JScrollPane(textArea);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setWheelScrollingEnabled(true);
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension screemSize=toolkit.getScreenSize();
		int screenWidth=screemSize.width;
		int screenHeight=screemSize.height;
		this.setLayout(null);
		this.setSize(screenWidth/2,screenHeight/2);
		this.setVisible(true);
		this.add(textArea);
		string=str;
		this.stockinfos=stockinfos;
		textArea.setBounds(5, 5, screenWidth/3, screenHeight/2);
		jScrollPane.setBounds(20, 5, screenWidth/3, screenHeight/2);
		this.add(jScrollPane);
		wordsCloud();
		ImageIcon imageIcon=new ImageIcon(".\\data.png");
		JLabel picture =new JLabel();
		picture.setIcon(imageIcon);
		picture.setBounds(0, screenHeight/3+350, 300, 300);
		this.add(picture);
		JButton saveButton=new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String originpath=(new File(".\\data.png")).getAbsolutePath();
				JFileChooser jf = new JFileChooser();  
				jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
				jf.showDialog(null, "Save");  
				File fi = jf.getSelectedFile();  
				String targetpath = fi.getAbsolutePath();
				String cmd = "cmd /c copy " + originpath +" "+ targetpath;	
				try {
					Runtime.getRuntime().exec(cmd).waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		saveButton.setBounds(350, screenHeight/3+350, 100,30);
		this.add(saveButton);
	}
	void wordsCloud() {
		TF_IDFImpl tf_IDFImpl=new TF_IDFImpl();
		ChineseSegmenterImpl chineseSegmenterImpl=new ChineseSegmenterImpl();
		SorterImpl sorterImpl=new SorterImpl();
		List<String >words=new LinkedList<>();
		try {
			words=
					sorterImpl.getKeyWords(tf_IDFImpl.getResult(chineseSegmenterImpl.getWordsFromString(string), stockinfos));
			Color[] colors = new Color[10];
	        for (int i = 0; i < colors.length; i++) {
	            colors[i] = new Color(
	                    (new Double(Math.random() * 128)).intValue() + 128,
	                    (new Double(Math.random() * 128)).intValue() + 128,
	                    (new Double(Math.random() * 128)).intValue() + 128);
	        }
	        WordCloudBuilder.buildWordCouldByWords(300,300,4,20,10,words,new Color(-1),"data.png",colors);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
