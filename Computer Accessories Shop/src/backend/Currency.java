package backend;

// create new class so that we can handle floats as £currency
public class Currency implements Comparable<Currency> {
    private Float value;
    
    //constructor
    public Currency(Float value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.format("£%.2f", this.value);
    }

    // override float compare function so that the innate TableView 'order by' ascending/descending will work
	@Override
	public int compareTo(Currency number) {
		return this.value.compareTo(number.value);
	}
	
	public Currency add(Currency number) {
		return new Currency(this.value + number.value);
	}
	

}
