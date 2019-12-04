package sth;
class Functionary extends User{

    Functionary(int id, int cellphone,String name){
        super(id, cellphone, name);
    }
    
	@Override
	boolean isFunctionary(){
		return true;
	}
	
	String getTypeString(){
        return "FUNCIONÁRIO";
    }
	String getSubjectString(){
        return "";
    }
}
