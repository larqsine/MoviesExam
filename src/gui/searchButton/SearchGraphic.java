package gui.searchButton;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utility.GraphicIdValues;

public class SearchGraphic implements ISearchGraphic{
    private  SVGPath svgPath;
    private String id;
    public SearchGraphic() {
        svgPath=new SVGPath();
        svgPath.setContent("M12 12 L17 17 M11 5 C11 7.76142 8.76142 10 6 10 C3.23858 10 1 7.76142 1 5 C1 2.23858 3.23858 0 6 0 C8.76142 0 11 2.23858 11 5 Z");
        svgPath.setStrokeWidth(2);
        svgPath.setStroke(Color.BLACK);
        svgPath.setFill(Color.WHITE);
        svgPath.setId(GraphicIdValues.SEARCH.getValue());
    }



    @Override
    public SVGPath getGraphic() {
        return this.svgPath;
    }

    @Override
    public void setGraphic(SVGPath path) {
        this.svgPath=path;
    }
}

