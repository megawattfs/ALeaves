package com.example.aleaves;
import java.sql.Date;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonDateTime;
import org.bson.BsonWriter;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class User {

    public static final String LEAF_DATABASE = "leaf_data";
    public static final String USER_COLLECTION = "all_users";

    private final ObjectId _id;
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final Date dob;
    private int numLeaves;


    /** Constructs a User from a MongoDB document. */
    User(
            final ObjectId _id,
            final String userId,
            final String firstName,
            final String lastName,
            final Date dob,
            int numLeaves
    ) {
        this._id = _id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.numLeaves = numLeaves;
    }

    public ObjectId getObjectId() { return _id; }

    public String getUserId() { return userId; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDob() { return dob; }

    public int getNumLeaves() { return numLeaves; }

    /* Convert java types to bson types */
    public long getLongDOB() { return dob.getTime(); }

    /* Convert bson types to java types */
    static Date longToDate(long longDOB) {
        Date convertedDate =  new Date(longDOB);
        return convertedDate;
    }

    static BsonDocument
    toBsonDocument(final User user) {
        final BsonDocument asDoc = new BsonDocument();
        asDoc.put(Fields.ID, new BsonObjectId(user.getObjectId()));
        asDoc.put(Fields.USER_ID, new BsonString(user.getUserId()));
        asDoc.put(Fields.FIRST_NAME, new BsonString(user.getFirstName()));
        asDoc.put(Fields.LAST_NAME, new BsonString(user.getLastName()));
        asDoc.put(Fields.DOB, new BsonDateTime(user.getLongDOB()));
        asDoc.put(Fields.NUM_LEAVES, new BsonInt32(user.getNumLeaves()));

        return asDoc;
    }

    static User fromBsonDocument(final BsonDocument doc) {
        return new User(
                doc.getObjectId(Fields.ID).getValue(),
                doc.getString(Fields.USER_ID).getValue(),
                doc.getString(Fields.FIRST_NAME).getValue(),
                doc.getString(Fields.LAST_NAME).getValue(),
                longToDate(doc.getDateTime(Fields.DOB).getValue()),
                doc.getInt32(Fields.NUM_LEAVES).getValue()
        );
    }

    static final class Fields {
        static final String ID = "_id";
        static final String USER_ID = "user_id";
        static final String FIRST_NAME = "first_name";
        static final String LAST_NAME = "last_name";
        static final String DOB = "date";
        static final String NUM_LEAVES = "num_leaves";
    }

    public static final Codec<User> codec = new Codec<User>() {

        @Override
        public void encode(
                final BsonWriter writer, final User value, final EncoderContext encoderContext) {
            new BsonDocumentCodec().encode(writer, toBsonDocument(value), encoderContext);
        }

        @Override
        public Class<User> getEncoderClass() {
            return User.class;
        }

        @Override
        public User decode(
                final BsonReader reader, final DecoderContext decoderContext) {
            final BsonDocument document = (new BsonDocumentCodec()).decode(reader, decoderContext);
            return fromBsonDocument(document);
        }
    };
}