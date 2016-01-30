package net.dragora.luigipapino_babylon.data.schematic_providers;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by nietzsche on 28/01/16.
 */
@Database(version = ContactsDataBase.VERSION)
public final class ContactsDataBase {
    public static final int VERSION =1;

    @Table(ContactColumns.class) public static final String CONTACTS = "contacts";
}
