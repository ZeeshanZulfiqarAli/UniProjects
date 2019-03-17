package application;

	import java.util.*;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.lang.String;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
	import java.time.ZoneId;
	import java.time.ZoneOffset;
	import java.io.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;


	public class Ibot{											
		static boolean stop=false,check=true,DataCopied=false,answered=false,gotoSubq=false,found=false;
		static long length;										
		static int[] checker={1,2,3,4,5,6,7,8};
		static int SubQnum=0;
		static int Qnum=0;

		public ArrayList<String> Handler(String UserSay) {
			ArrayList<String> list = null;
			File tempFilePath=new File("dbTemp.txt");
			UserSay=UserSay.toLowerCase();
			UserSay=UserSay.replaceAll(" you ", " u ");
			UserSay=UserSay.replaceAll(" are ", " r ");
			System.out.println(UserSay);
			try {
				tempFilePath.createNewFile();
			} catch (IOException e) {
				System.out.println("Temporary file already present!");
			}
			tempFilePath.deleteOnExit();
			try {												
			list=BotSays(UserSay,tempFilePath);
			}
			catch(Exception e){				
					System.out.println("Error1");
			}	
			return list;
		}    
		
		public static ArrayList<String> BotSays(String UserSay,File tempFilePath) throws InterruptedException {
			StringBuilder tempe;
			ArrayList<String> out=new ArrayList<String>();						//array list used because in string array we need to specify the number of rows, but in list u don't have to..
			String BOutput=null,binput = null;
			int timesAsked = 0,answers=0,timesAskedIndex = 0;
			long pointer=0;
			char hold;
			String convertHold;
			if(UserSay.contains("time")) {
				String h=timeZone(UserSay);
				if(found){
					found=false;
				out.add(h);
				return out;
				}
			}  
			if(UserSay.contains("bye")||UserSay.contains("exit")||UserSay.contains("quit")||UserSay.contains("ttyl"))
			{
				stop=true;
				out.add("ok bye");
				return out;
			}
			else if(UserSay.contains("jokes")||UserSay.contains("joke") ) {
				out= jokes();
				return out;
			}
			else if (UserSay.contains("browser")||UserSay.contains("youtube")||UserSay.contains("web")) {
				
				out= web(UserSay);
				System.out.println("out= "+out.get(0));
				return out;
				
			}
			else if(UserSay.contains("news")) {
				out.add("lemme get it for ya...");
				out.addAll(news());
				return out;
			}
			 else if(UserSay.contains("weather")||UserSay.contains("temperature")||UserSay.contains("temp")) {

				 
				out.add("wait. let me grab my thermometer...");
				try {
					out.add("It seems to me that it is "+weatherClass2.main(null)+(char)176+"C");
				} catch (Exception e) {
					
					out.add( "Sorry can't check weather rn. Lost my theromometer again!");
				}
				finally {
					return out;
				}
			}

			else 
			{ 	
			    int dotIndex,firstIndex,secondIndex,thirdIndex,fourthIndex;

				RandomAccessFile file=null;
				RandomAccessFile fileTemp=null;
				RandomAccessFile Suggestion=null;
				try {
					Suggestion=new RandomAccessFile(new File("suggestions.txt"),"rw");
				} catch (FileNotFoundException e3) {
				System.out.println("suggestion file not found");
				}
				try {
					fileTemp=new RandomAccessFile(tempFilePath,"rw");
					
				} catch (FileNotFoundException e2) {
					System.out.println("Some problem while opening temp file");	
					}

				String str;
			if(gotoSubq ){
			str=Subq(UserSay);
			if(answered) {
				out.add(str);
				return out;
			}
			}
						
			if(!DataCopied){
				File filePathOfdb=new File("db.txt");
			try{
				file=new RandomAccessFile(filePathOfdb,"rw");
				length=file.length();
				
			}
			catch(Exception e){
				System.out.println("File not found");
				return out;
			}

			try{
			for(int h=0;h<length;h++) 
				{
				fileTemp.seek(h);
				file.seek(h);
				fileTemp.writeByte(file.read());	
				
				DataCopied=true;
				
				}
					//close file after its copied
			}
			catch(Exception e){
				e.printStackTrace();
				try {
					file.close();
					fileTemp.close();
					fileTemp.getChannel().close();
					
				} catch (IOException e1) {
					System.out.println("Error while closing file");
				}

				return null;	
			}
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			}
			int lineStart=0;
			pointer=0;
			answers=0;
			try{
				
		    	try{
				fileTemp.seek(pointer);
			}
			catch(Exception e){
				System.out.println("the error is all about file.seek");
			}
			    redo: do{
			    	lineStart=(int)pointer;
			    	tempe=new StringBuilder(1);
				hold=(char)fileTemp.read();
				pointer++;
				do
				{
					if(hold>=65 && hold<=90){
						hold+=32;
						}
					 tempe.append(hold);
					 hold=(char)fileTemp.read();
					 pointer++;
					 if (hold=='\n'){
						 tempe.delete(0,tempe.length());
						 continue redo;
					 }
				}
				while(hold!=']' && pointer<length);   //length is index+1
				binput=new String(tempe.toString());
				tempe.append(hold);
				if(pointer>=length){

					try{
					Suggestion.seek(Suggestion.length());
					Suggestion.writeBytes(UserSay+"\r\n");}
				catch(Exception e4){e4.printStackTrace();}
					try {
						fileTemp.close();
						fileTemp.getChannel().close();
						
					} catch (IOException e1) {
						System.out.println("Error while closing file");
					}
					out.add(0,"hey I can't Understand what are you saying. Maybe i should really listen to mom and start learning dictionary...");
					return out;
					
				}
			}while(!(binput.contains(UserSay)));												//sort the values out of the line read...
		    	dotIndex=binput.indexOf('.');
		    	firstIndex=binput.indexOf('>');
		    	secondIndex=binput.indexOf('>',firstIndex+1);
		    	thirdIndex=binput.indexOf('>',secondIndex+1);
		    	fourthIndex=binput.indexOf('>',thirdIndex+1);
		    	Qnum=Integer.parseInt(binput.substring(binput.indexOf('.')+1,firstIndex));
		    	SubQnum=Integer.parseInt(binput.substring(firstIndex+1,secondIndex));
		    	timesAskedIndex=(int)pointer-((fourthIndex+1)-(secondIndex+1)+1);
				timesAsked=Integer.parseInt(binput.substring(secondIndex+1,thirdIndex));
				pointer=timesAskedIndex;
				answers=Integer.parseInt(binput.substring(thirdIndex+1,fourthIndex));
				BOutput=fileTemp.readLine();
				pointer=fileTemp.getFilePointer();
			}
			catch(Exception e){
				System.out.println("error is here 1");
				try {
					fileTemp.close();
				} catch (IOException e1) {
					System.out.println("Error while closing file");
				}
				e.printStackTrace();
				try {
					fileTemp.close();
					fileTemp.getChannel().close();
					
				} catch (IOException e1) {
					System.out.println("Error while closing file");
				}

				out.add(0,"Some error came");
				return out;
			}
			try{
					int b=timesAsked+1;
					if(timesAsked>answers)
						b=answers+1;
					timesAsked++;
					for(;b>0;b--)
					{
						BOutput=fileTemp.readLine();
					}
					
					
			}
			catch (Exception e){
				BOutput="I can't Understand what are you saying. Maybe i should really listen to mom and start learning dictionary...";				
			}
			StringBuilder copyHold=null;

			char ch;
			try{
			fileTemp.seek(0);
			copyHold=new StringBuilder((int) fileTemp.length());
			int ftLen=(int)fileTemp.length(); 
			for(int l=0;l<=ftLen;l++){
				if(l!=timesAskedIndex){
				ch=(char)fileTemp.read();
				copyHold.append(ch);
				}
				else{
					l=thirdIndex+lineStart;
					fileTemp.seek(l);
					copyHold.append(timesAsked);
				}
			}}
			catch(Exception e){
				e.printStackTrace();
			}
			convertHold=new String(copyHold);
			try{
				fileTemp.seek(0);
				byte[] tempByte;
				tempByte=convertHold.getBytes("Windows-1252");
				convertHold = new String(tempByte, "Windows-1252");
				for(byte b: tempByte) {
					fileTemp.writeByte(b);
		         }
			}
			catch(Exception e){
				e.printStackTrace();
				try {
					fileTemp.close();
					fileTemp.getChannel().close();
					
				} catch (IOException e1) {
					System.out.println("Error while closing file");
				}
				System.out.println("Error in writing file");
				return out;
			}

			try {
				fileTemp.close();
				fileTemp.getChannel().close();
				
			} catch (IOException e1) {
				System.out.println("Error while closing file");
			}

			if(SubQnum!=0)
				gotoSubq=true;
			out.add(0,BOutput);
			return out;
			}
			

	}

	public static ArrayList<String> jokes() throws InterruptedException {
		Random rand=new Random();
		ArrayList<String> out=new ArrayList<String>();
		Scanner scan=null;
		try{
		scan=new Scanner(new File("jokes.txt"));
		}
		catch(Exception e){
			System.out.println("File not found");
		}
		String hold;
		int a=0,b=0,temp;

		do {
		check=true;
		b=0;
		a=0;
		temp=rand.nextInt(8)+1;
		for(a=1;a<=8;a++)
		{
		if(temp==checker[a-1]) 
			{
			checker[a-1]=0;
			check=false;
			break;
			}
		}
		for(int j:checker) {
			if(j==0) {
				b++;
			}
			if(b==8){
			out.add(0,"you confused me!! now i forgot all other jokes i had in my mind for you!!");
			out.add(1,"go now. And come back tommrrow!");
			check=false;
			return out;
		}
		}
		} while(check);
		String RandomNum=Integer.toString(temp);
		do {
			hold=scan.nextLine();

		} while (!hold.contains(RandomNum+"] ")); 
		hold=hold.replace(RandomNum+"] ","");
		int count=0;
		do {
				out.add(count,hold);
				hold=scan.nextLine();
				count++;
		} while(!hold.contains("<end>"));
		return out;
	}

	public static String Subq(String input){
		RandomAccessFile sq;
		String hold=null,check=null;
		boolean cont=true;
		int k=0;
		try {
			sq=new RandomAccessFile(new File("subquestions.txt"),"rw");
			}
		catch(Exception e){return "error in followup question file";}
		
		try{
		
		do{
			
		hold=sq.readLine();
		
		if(hold==null) {			//no matches found
			gotoSubq=false;			//boolean flag which sets if another question to be checked here or not.      //answered flag works same
			answered=false;
			sq.close();
			return "";
		}
		if(hold.contains("]")){
		k=Integer.parseInt(hold.substring(0,hold.indexOf(']')));
		if(Qnum==k) {
			if(hold.contains(input)){
				gotoSubq=true;
				answered=true;
				check=sq.readLine();
				sq.close();
				return (check);
			}
		}
		}}
		while(cont);}
		catch(Exception e) {System.out.println("Error in Subq method");}
	return ".";

	}

	public static String timeZone(String UserSay){
		String hold=null,country=null;
		LocalDateTime ldt;
		long pos=0,len = 0;
		RandomAccessFile timeZones=null;
		try {
			timeZones=new RandomAccessFile("tz.txt","rw");
		} catch (FileNotFoundException e) {
			System.out.print("Error in file tz");
		}
		try {
			len=timeZones.length();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		do{
			 try {
				hold=timeZones.readLine();
			} catch (IOException e) {
				System.out.println("Error in reading tz file");
			}
		try {
			pos=timeZones.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(pos>=len){
			found=false;
			return null;
		}
		
		}
		while(!UserSay.contains(hold));
		country=hold;
		try {
			hold=timeZones.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ldt=LocalDateTime.now(ZoneId.ofOffset("UTC",ZoneOffset.of(hold)));
		found=true;
		StringBuilder temp=new StringBuilder(ldt.toString());
		int index=temp.indexOf("T");
		String date=temp.substring(0,index);
		temp.deleteCharAt(index);
		return "Time in "+country+" is "+temp.substring(index, temp.length())+" and date is "+date;
	}

	public static ArrayList<String> web(String UserInput){
		
		ArrayList<String> output = new ArrayList<String>();
		Stage stage = new Stage();
		String url = null;
	    output.add("wait...");
	    try {
			if(!InternetConn.isInternetAvailable()) {
				output.add("Oops cant connect rn...");
				return output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(UserInput.contains("youtube")) {
		url = "https://www.youtube.com";
		}

		if(UserInput.contains("browser")) {
			url = "https://www.google.com";			
		}
		WebView webview = new WebView();
	    webview.getEngine().load(url);
	    webview.setPrefSize(640, 390);
	    stage.setScene(new Scene(webview));
	    stage.setTitle("Browser");
	    Timeline tl=new Timeline();
	    KeyFrame[] kf=new KeyFrame[1];
		kf[0]=new KeyFrame(Duration.seconds(2),new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
			    stage.show();
			} 
		});
		tl.getKeyFrames().add(kf[0]);
		tl.play();
		return output;
	}

	public static ArrayList<String> news() {
		ArrayList<String> out=new ArrayList<String>();
		URLConnection  feedUrl=null;
		int count=0;
		try {
			feedUrl = new URL("https://www.geo.tv/rss/1/0").openConnection();
			feedUrl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/604.1 (KHTML, like Gecko) JavaFX/8.0 Safari/604.1");
		}
		catch(Exception e) {
			out.add("sorry cant connect rn");
			return out;
		}

         SyndFeedInput input = new SyndFeedInput();
         SyndFeed feed = null;
		try {
			feed = input.build(new XmlReader(feedUrl));
		} catch(Exception e) {
			out.add("sorry cant connect rn");
			return out;
		}

         System.out.println(feed);
		for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            System.out.println("Title: " + entry.getTitle());
            out.add(entry.getTitle());
            count++;
		}
//		System.out.println(count);
		return out;
	}

}
	
class InternetConn{
	public static boolean isInternetAvailable() throws IOException
	{
	    return isHostAvailable("google.com") || isHostAvailable("amazon.com")
	            || isHostAvailable("facebook.com")|| isHostAvailable("apple.com");
	}

	private static boolean isHostAvailable(String hostName) throws IOException
	{
	    try(Socket socket = new Socket())
	    {
	        int port = 80;
	        InetSocketAddress socketAddress = new InetSocketAddress(hostName, port);
	        socket.connect(socketAddress, 3000);

	        return true;
	    }
	    catch(UnknownHostException unknownHost)
	    {
	        return false;
	    }
	}
}