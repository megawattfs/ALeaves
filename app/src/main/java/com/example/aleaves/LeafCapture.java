package com.example.aleaves;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Environment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonDateTime;
import org.bson.BsonWriter;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class LeafCapture {

    public static final String LEAF_DATABASE = "leaf_data";
    public static final String LEAF_COLLECTION = "all_leaves";

    private final ObjectId _id;
    private final String owner_id;
    private final String location;
    private final Date date;
    private final Bitmap image;

    /** Constructs a LeafCapture from a MongoDB document. */
    LeafCapture(
            final ObjectId id,
            final String owner_id,
            final String location,
            final Date date,
            final Bitmap image
    ) {
        this._id = id;
        this.owner_id = owner_id;
        this.location = location;
        this.date = date;
        this.image = image;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Bitmap getImage() { return image; }

    /* Convert java types to bson types */
    public long getLongDateTime() {
        return date.getTime();
    }

    public String getImageString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] bsonImage = baos.toByteArray();
        String encImage = Base64.encodeToString(bsonImage, Base64.DEFAULT);
        return encImage;
    }

    /* Convert bson types to java types */
    static Date longToDate(long longDate) {
        Date convertedDate =  new Date(longDate);
        return convertedDate;
    }

    static Bitmap stringToImage(String imageString) {
        byte[] decImage = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap bmp=BitmapFactory.decodeByteArray(decImage,0,decImage.length);
        return bmp;
    }

    static BsonDocument
    toBsonDocument(final LeafCapture item) {
        final BsonDocument asDoc = new BsonDocument();
        asDoc.put(Fields.ID, new BsonObjectId(item.get_id()));
        asDoc.put(Fields.OWNER_ID, new BsonString(item.getOwner_id()));
        asDoc.put(Fields.LOCATION, new BsonString(item.getLocation()));
        asDoc.put(Fields.DATE, new BsonDateTime(item.getLongDateTime()));
        asDoc.put(Fields.IMAGE, new BsonString(item.getImageString()));
        return asDoc;
    }

    static LeafCapture fromBsonDocument(final BsonDocument doc) {
        return new LeafCapture(
                doc.getObjectId(Fields.ID).getValue(),
                doc.getString(Fields.OWNER_ID).getValue(),
                doc.getString(Fields.LOCATION).getValue(),
                longToDate(doc.getDateTime(Fields.DATE).getValue()),
                stringToImage(doc.getString(Fields.IMAGE).getValue())
        );
    }

    static final class Fields {
        static final String ID = "_id";
        static final String OWNER_ID = "owner_id";
        static final String LOCATION = "location";
        static final String DATE = "date";
        static final String IMAGE = "image";
    }

    public static final Codec<LeafCapture> codec = new Codec<LeafCapture>() {

        @Override
        public void encode(
                final BsonWriter writer, final LeafCapture value, final EncoderContext encoderContext) {
            new BsonDocumentCodec().encode(writer, toBsonDocument(value), encoderContext);
        }

        @Override
        public Class<LeafCapture> getEncoderClass() {
            return LeafCapture.class;
        }

        @Override
        public LeafCapture decode(
                final BsonReader reader, final DecoderContext decoderContext) {
            final BsonDocument document = (new BsonDocumentCodec()).decode(reader, decoderContext);
            return fromBsonDocument(document);
        }
    };
}
