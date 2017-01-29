package com.agrass.coffeemap.model.map;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.XYTileSource;

public class RetinaTileSource extends XYTileSource {
    public RetinaTileSource(final String aName, final int aZoomMinLevel, final int aZoomMaxLevel,
                            final int aTileSizePixels, final String aImageFilenameEnding, final String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    @Override
    public String getTileURLString(final MapTile aTile) {
        return getBaseUrl() + aTile.getZoomLevel() + "/" + aTile.getX() + "/" + aTile.getY()
                + mImageFilenameEnding + "?tag=retina";
    }
}
