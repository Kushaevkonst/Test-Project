package poligonsproject;

import java.util.List;

/**
 *
 * @author дом
 */
public class Vertices {

    private List<Vertice> vertices;
    public Vertices() {
    }
    public Vertices(List<Vertice> vertices) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("List<Vertice> should be greater then 2");
        }
        this.vertices = vertices;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public class Vertice {

        Vertice(float x, float y) {
            this.x = x;
            this.y = y;
        }

        private float x;

        public float getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        private float y;

        public float getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%f; %f)", x, y);

        }
    }

}
