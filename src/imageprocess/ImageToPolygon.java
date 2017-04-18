///**
// * 
// */
//package imageprocess;
//
//import java.util.List;
//import java.util.Set;
//
//import javafx.geometry.Point2D;
//import javafx.scene.image.Image;
//import javafx.scene.shape.Polygon;
//
///**
// * 
// * There are two choices to create a hull of the image
// * one is convex hull of an image, another is the plain
// * hull of an image. One can choose one of those by commenting
// *another line
// * @author Zhiyong
// *
// */
//public class ImageToPolygon {
//
//	private Image image;
//	public ImageToPolygon(Image image){
//		this.image =image;
//	}
//
//	/**
//	 * @return Polygon
//	 * Create a polygon hull of the given image by deleting the transparent pixels
//	 * There are two methods: convex_hull and hull
//	 */
//	public Polygon getPolygon(){
//		ImageTransformation transformation = new ImageTransformation();
//		Set<Point2D> set = transformation.getMask(image);
//
//		//choose one of the convexhull or hull to get the hull points
//
//
//		//This is the Hull class
//		List<Point2D> convexHullPointTemp =  Hull.getHull(set); 
//		
//		//This is the convexHull class
//		List<Point2D> convexHullPoint =  ConvexHull.convex_hull(convexHullPointTemp); 
//		double[] pointsToDraw = new double[2 * convexHullPoint.size()];
//		int k = 0;
//		for(Point2D p : convexHullPoint){
//			pointsToDraw[k] = p.getX();
//			pointsToDraw[k + 1] = p.getY();
//			k += 2;
//		}
//
//		Polygon pol = new Polygon( pointsToDraw);		
//		return pol;
//	}
//}
