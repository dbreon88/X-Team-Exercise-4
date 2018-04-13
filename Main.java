
public class Main {

	public static void main(String[] args) {
		try{
			WordProcessor proc = new WordProcessor();
		} catch (Exception e){
			System.out.println(e.getStackTrace());
		}
		
	}

}
