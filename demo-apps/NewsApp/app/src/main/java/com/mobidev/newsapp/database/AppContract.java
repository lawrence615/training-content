package com.mobidev.newsapp.database;

/**
 * @author lawrence
 */

import android.net.Uri;
import android.provider.BaseColumns;

public class AppContract {

    public interface NewsColumns {
        public static final String NEWS_ID = "news_id";
        public static final String NEWS_TYPE_ID = "news_type_id";
        public static final String SLUG = "slug";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String IMAGE_URL = "image_url";
        public static final String CREATED_AT = "created_at";
    }

    //content://com.mobidev.newsapp/news/1

    public static final String CONTENT_AUTHORITY = "com.mobidev.newsapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY);

    private static final String PATH_NEWS = "news";

    public static class News implements NewsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NEWS).build();

        public static final String DEFAULT_SORT = NewsColumns.CREATED_AT
                + " DESC";

        public static Uri buildPostUri(String blockId) {
            return CONTENT_URI.buildUpon().appendPath(blockId).build();
        }

        public static String getNewsId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

}
