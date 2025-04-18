import models.App;
import models.Tile;
import models.enums.TileData;
import views.AppView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        loadTiles();
        (new AppView()).run();
    }

    private static void loadTiles() {
        for (TileData tileData : TileData.values()) {
            int id = tileData.getId();
            String country = tileData.getCountry();
            List<Integer> landNeighbors = tileData.getLandNeighbors();
            List<Integer> seaNeighbors = tileData.getSeaNeighbors();

//				if(!parts[2].isEmpty()) {
//					landNeighbors = Arrays.stream(parts[2].split(";"))
//							.map(Integer::parseInt).collect(Collectors.toList());
//				}
//				if(!parts[3].isEmpty()) {
//					seaNeighbors = Arrays.stream(parts[3].split(";"))
//							.map(Integer::parseInt)
//							.collect(Collectors.toList());
//				}
            Tile tile = new Tile(id, country, landNeighbors, seaNeighbors);
            App.addTile(tile);
            tile.getCountryType().addTile(tile);
        }
    }
}
