package gui.searchButton;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import utility.GraphicIdValues;

public class UndoGraphic implements ISearchGraphic {
    private SVGPath svgPath ;

    public UndoGraphic() {
        svgPath=new SVGPath();
        svgPath.setContent("M10 8H5V3 M5.29102 16.3569C6.22284 17.7918 7.59014 18.8902 9.19218 19.4907C10.7942 20.0913 12.547 20.1624 14.1925 19.6937C15.8379 19.225 17.2893 18.2413 18.3344 16.8867C19.3795 15.5321 19.963 13.878 19.9989 12.1675C20.0347 10.4569 19.5211 8.78001 18.5337 7.38281C17.5462 5.98561 16.1366 4.942 14.5122 4.40479C12.8878 3.86757 11.1341 3.86499 9.5083 4.39795C7.88252 4.93091 6.47059 5.97095 5.47949 7.36556");
        svgPath.setStrokeWidth(2);
        svgPath.setStroke(javafx.scene.paint.Color.BLACK);
        svgPath.setStrokeLineCap(StrokeLineCap.ROUND);
        svgPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
        svgPath.setFill(Color.WHITE);
        svgPath.setId(GraphicIdValues.UNDO.getValue());
    }


    @Override
    public SVGPath getGraphic() {
        return this.svgPath;
    }

    @Override
    public void setGraphic(SVGPath path) {
        this.svgPath = path;
    }
}
