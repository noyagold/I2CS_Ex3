package assignments.Ex2;

public class Index2D implements Pixel2D {
    private int x;
    private int y;
    public Index2D(int w, int h) {
       this.x = w;
        this.y = h;
    }

    public Index2D(Pixel2D other) {
         this.x = other.getX();
         this.y=other.getY();
    }
    @Override
    public int getX() {

        return this.x;
    }

    @Override
    public int getY() {

        return this.y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        if (p2==null){
        throw new RuntimeException();}
        double distance = Math.sqrt(Math.pow(p2.getX()-x,2)+Math.pow(p2.getY()-y,2));

        return distance;
    }

    @Override
    public String toString() {
         String ans = "(" + this.x + ","+ this.y + ")";
        return ans;
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = false;
        if (p==null){return ans;}
        if (p instanceof Index2D ) {        //checks if p is from class Index2D
            Index2D pp = (Index2D) p;       // looks at p as an object from Index2D  (casting)
           if (pp.getX()==this.x && pp.getY()==this.y) {   //object.method
               ans = true;
           }
        }
        return ans;
    }
}
