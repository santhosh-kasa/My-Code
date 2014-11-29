
public class testobj {
	int i=0;
	String s;
	price p;

	public class price {
		int price1;
		int price2;
		price(int p1,int p2) {
			price1 = p1;
			price2 = p2;
		}
		
	public String toString(){
		return price1 + " " + price2;
	}
	}
	
	testobj(int n, String name){
	i =n;
	s = name;
	p = new price(n+1,n+2);
	}
	
	public String toString(){
		return  s + "  "+ p.toString();
	}
}
