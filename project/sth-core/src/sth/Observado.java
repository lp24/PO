package sth;

interface Observado{
    public void addObserver(Observador o);
    public void removeObserver(Observador o);
    public void notify(String s);
}

