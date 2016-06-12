/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poligonsproject;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import poligonsproject.Poligon.Segment;
import poligonsproject.Vertices.Vertice;

/**
 *
 * @author дом
 */
public class Poligon extends Vertices {

    public float getArea(List<Vertice> vertices) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("List<Vertice> should be greater then 2");
        }
        float area = 0;
        for (int i = 0; i < (vertices.size() - 1); i++) {
            area += ((vertices.get(i).getX() + vertices.get(i + 1).getX()) * (vertices.get(i).getY() - vertices.get(i + 1).getY()));
        }
        area += ((vertices.get(vertices.size() - 1).getX() + vertices.get(0).getX()) * (vertices.get(vertices.size() - 1).getY() - vertices.get(0).getY()));
        return Math.abs(area) / 2;
    }

    List<Vertice> getPoligonFromCrossTwoPoligons(List<Vertice> vertices1, List<Vertice> vertices2) {
        if (vertices1.size() < 3 || vertices2.size() < 3) {
            throw new IllegalArgumentException("List<Vertice> should be greater then 2");
        }
        ArrayList<Segment> segments1 = new ArrayList<Segment>();
        for (int i = 0; i < vertices1.size() - 1; i++) {
            segments1.add(new Segment(vertices1.get(i), vertices1.get(i + 1)));
        }
        segments1.add(new Segment(vertices1.get(vertices1.size() - 1), vertices1.get(0)));
        ArrayList<Segment> segments2 = new ArrayList<Segment>();
        for (int i = 0; i < vertices2.size() - 1; i++) {
            segments2.add(new Segment(vertices2.get(i), vertices2.get(i + 1)));
        }
        segments2.add(new Segment(vertices2.get(vertices2.size() - 1), vertices2.get(0)));
        ArrayList<Vertice> poligon = new ArrayList<Vertice>();
        for (Segment segment1 : segments1) {
            if (VerticesInPoligon(segment1.startPoint, segments2) == true) {
                poligon.add(segment1.startPoint);
            }
            for (Segment segment2 : segments2) {

                if (IsSegmenetsCross(segment1, segment2) == true) {
                    poligon.add(SegmentsCrossPoint(segment1, segment2));
                }
            }
        }
        for (Segment segment2 : segments2) {
            if (VerticesInPoligon(segment2.startPoint, segments1) == true) {
                poligon.add(segment2.startPoint);
            }
        }
        return poligon;
    }

    private boolean IsSegmenetsCross(Segment firstSegment, Segment secondSegment) {
        float x11 = firstSegment.startPoint.getX();
        float x12 = firstSegment.finishPoint.getX();
        float y11 = firstSegment.startPoint.getY();
        float y12 = firstSegment.finishPoint.getY();
        float x21 = secondSegment.startPoint.getX();
        float x22 = secondSegment.finishPoint.getX();
        float y21 = secondSegment.startPoint.getY();
        float y22 = secondSegment.finishPoint.getY();
        float dx1 = x12 - x11, dy1 = y12 - y11;
        float dx2 = x22 - x21, dy2 = y22 - y21;
        float dx31 = x11 - x21, dy31 = y11 - y21;
        float dx32 = x12 - x21, dy32 = y12 - y21;
        float dx14 = x22 - x11, dy14 = y22 - y11;
        float maxx1 = max(x11, x12);
        float maxy1 = max(y11, y12);
        float minx1 = min(x11, x12);
        float miny1 = min(y11, y12);
        float maxx2 = max(x21, x22);
        float maxy2 = max(y21, y22);
        float minx2 = min(x21, x22);
        float miny2 = min(y21, y22);

        float div = dy2 * dx1 - dx2 * dy1;

        float v1 = dx2 * dy31 - dy2 * dx31;
        float v2 = dx2 * dy32 - dy2 * dx32;
        float v3 = dx1 * (-dy31) - dy1 * (-dx31);
        float v4 = dx1 * dy14 - dy1 * dx14;
        float vol1 = v1 * v2;
        float vol2 = v3 * v4;
        if (div == 0 & vol1 == 0 & vol2 == 0) {
            if (minx1 > maxx2 || maxx1 < minx2 || miny1 > maxy2 || maxy1 < miny2) {
                return false;
            }
        }
        return vol1 < 0 & vol2 < 0;
    }

    private Vertice SegmentsCrossPoint(Segment firstSegment, Segment secondSegment) {
        float a1 = (firstSegment.finishPoint.getY() - firstSegment.startPoint.getY());
        float a2 = (secondSegment.finishPoint.getY() - secondSegment.startPoint.getY());
        float b1 = (firstSegment.startPoint.getX() - firstSegment.finishPoint.getX());
        float b2 = (secondSegment.startPoint.getX() - secondSegment.finishPoint.getX());
        float c1 = firstSegment.startPoint.getX() * (-a1) + firstSegment.startPoint.getY() * (-b1);
        float c2 = secondSegment.startPoint.getX() * (-a2) + secondSegment.startPoint.getY() * (-b2);
        float D = a1 * b2 - b1 * a2;
        float Dx = -c1 * b2 + c2 * b1;
        float Dy = -a1 * c2 + a2 * c1;
        float X = Dx / D;
        float Y = Dy / D;
        return new Vertice(X, Y);
    }

    private boolean VerticesInPoligon(Vertice vertice1, List<Segment> segments2) {
        float y = finishPoint(vertice1.getY(), segments2);
        int k = 0;
        for (Segment segment : segments2) {
            if (IsSegmenetsCross(new Segment(vertice1, new Vertice(vertice1.getX(), y)), segment) == true) {
                k++;
            }
        }
        return k % 2 == 1;
    }

    private float finishPoint(float y, List<Segment> segments) {
        for (Segment segment : segments) {
            if (y < max(segment.startPoint.getY(), segment.finishPoint.getY())) {
                y = max(segment.startPoint.getY(), segment.finishPoint.getY());
            }
        }
        y = y + 1;
        return y;
    }

    protected class Segment {

        private Vertice startPoint;
        private Vertice finishPoint;

        public Segment(Vertice startPoint, Vertice finishPoint) {
            this.startPoint = startPoint;
            this.finishPoint = finishPoint;
        }

        public Vertice getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(Vertice startPoint) {
            this.startPoint = startPoint;
        }

        public Vertice getFinishDot() {
            return finishPoint;
        }

        public void setFinishDot(Vertice finishDot) {
            this.finishPoint = finishDot;
        }

    }

}
