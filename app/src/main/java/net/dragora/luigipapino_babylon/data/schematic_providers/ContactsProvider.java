package net.dragora.luigipapino_babylon.data.schematic_providers;

import android.net.Uri;
import android.support.annotation.NonNull;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by nietzsche on 28/01/16.
 */
@ContentProvider(authority = ContactsProvider.AUTHORITY, database = ContactsDataBase.class)
public final class ContactsProvider {

    public static final String AUTHORITY = "net.dragora.luigipapino_babylon.provider.ContactsProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(@NonNull String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = ContactsDataBase.CONTACTS) public static class Contacts {

        @ContentUri(
                path = ContactsDataBase.CONTACTS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = ContactColumns.JSON + " ASC")
        public static final Uri CONTACTS = Uri.parse("content://" + AUTHORITY + "/" + ContactsDataBase.CONTACTS);

        @InexactContentUri(
                path = ContactsDataBase.CONTACTS + "/#",
                name = "CONTACT_ID",
                type = "vnd.android.cursor.item/vnd.net.dragora.luigipapino_babylon.contact",
                whereColumn = ContactColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(ContactsDataBase.CONTACTS , String.valueOf(id));
        }
    }
}
