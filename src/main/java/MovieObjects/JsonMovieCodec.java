package MovieObjects;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Class, which working with json file
 */
public class JsonMovieCodec {
    /**
     * Method, which parse json file and deserialize Hashtable
     * @param fileName name of json file
     * @return parsed Hashtable
     * @throws IOException if there are some problems with file
     * @throws FieldException if fields in file are incorrect
     */
    public static Hashtable<Integer, Movie> readFromFile(String fileName) throws IOException, FieldException {
        File movieFile = new File(fileName);
        if (movieFile.createNewFile()) {
            return new Hashtable<>();
        }
        if (!movieFile.canRead()) {
            throw new IOException("file " + fileName + " can't be read");
        }
        Scanner scanner = new Scanner(movieFile);
        String hashTableString = scanner.nextLine();
        scanner.close();

        Hashtable<Integer, Movie> resultHashtable = new Hashtable<>();

        JsonParser parser = Json.createParser(new StringReader(hashTableString));
        parser.next();

        JsonObject dataObject = parser.getObject();
        Movie.loadGlobalId(dataObject.get("global_id").toString());

        JsonArray movieArray = dataObject.getJsonArray("data");

        for (JsonValue jsonValue : movieArray) {
            JsonObject hashTableElementJsonObject = jsonValue.asJsonObject();

            Integer key = hashTableElementJsonObject.getInt("key");
            JsonObject movieJsonObject = hashTableElementJsonObject.getJsonObject("value");

            Movie movie = parseMovie(movieJsonObject);

            resultHashtable.put(key, movie);
        }

        return resultHashtable;
    }

    /**
     * Method, which parse only one element from collection
     * @param movieJsonObject JsonObject of one element
     * @return parsed element
     * @throws FieldException if fields in JsonObject are incorrect
     */
    private static Movie parseMovie(JsonObject movieJsonObject) throws FieldException {
        JsonObject coordinatesJsonObject = movieJsonObject.getJsonObject("coordinates");
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesJsonObject.get("x").toString());
        coordinates.setY(coordinatesJsonObject.get("y").toString());

        JsonObject screenwriterJsonObject = movieJsonObject.getJsonObject("screenwriter");
        Person screenwriter = new Person();
        screenwriter.setName(screenwriterJsonObject.getString("name"));
        String birthdayString = screenwriterJsonObject.getString("birthday");
        if (birthdayString.equals("null")) {
            birthdayString = null;
        }
        screenwriter.setBirthday(birthdayString);
        String hairColorString = screenwriterJsonObject.getString("hairColor");
        if (hairColorString.equals("null")) {
            hairColorString = null;
        }
        screenwriter.setHairColor(hairColorString);

        Movie movie = new Movie(
                Long.parseLong(movieJsonObject.get("id").toString()),
                ZonedDateTime.parse(movieJsonObject.getString("creationDate"))
        );
        movie.setName(movieJsonObject.getString("name"));
        movie.setCoordinates(coordinates);
        movie.setOscarsCount(movieJsonObject.get("oscarsCount").toString());
        movie.setLength(movieJsonObject.get("length").toString());
        String genreString = movieJsonObject.getString("genre");
        if (genreString.equals("null")) {
            genreString = null;
        }
        movie.setGenre(genreString);
        movie.setMpaaRating(movieJsonObject.getString("mpaaRating"));
        movie.setScreenwriter(screenwriter);

        return movie;
    }

    /**
     * Method, which pack json file and serialize Hashtable
     * @param fileName name of file
     * @param movieHashtable Hashtable we need to serialize
     * @throws IOException if there are some problems with file
     */
    public static void writeToFile(String fileName, Hashtable<Integer, Movie> movieHashtable) throws IOException {
        File movieFile = new File(fileName);
        movieFile.createNewFile();

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("global_id", Movie.global_id);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Integer integer : movieHashtable.keySet()) {
            Movie currentMovie = movieHashtable.get(integer);
            arrayBuilder.add(buildHashTableElement(integer, currentMovie));
        }

        objectBuilder.add("data", arrayBuilder);

        String dataString = objectBuilder.build().toString();

        outputStream.write(dataString.getBytes());

        outputStream.close();
    }

    /**
     * Method, which serialize only one element
     * @param key key of element
     * @param currentMovie value of element
     * @return serialized JsonObject
     */
    private static JsonObject buildHashTableElement(Integer key, Movie currentMovie) {
        Coordinates currentCoordinates = currentMovie.getCoordinates();
        Person currentPerson = currentMovie.getScreenwriter();

        JsonObject coordinates = Json.createObjectBuilder()
                .add("x", currentCoordinates.getX())
                .add("y", currentCoordinates.getY())
                .build();

        String currentHairColor;
        try {
            currentHairColor = currentPerson.getHairColor().toString();
        } catch (NullPointerException e) {
            currentHairColor = "null";
        }
        JsonObject person = Json.createObjectBuilder()
                .add("name", currentPerson.getName())
                .add("birthday", currentPerson.getBirthdayString())
                .add("hairColor", currentHairColor)
                .build();

        String currentGenre;
        try {
            currentGenre = currentMovie.getGenre().toString();
        } catch (NullPointerException e) {
            currentGenre = "null";
        }
        JsonObject movie = Json.createObjectBuilder()
                .add("id", currentMovie.getId())
                .add("name", currentMovie.getName())
                .add("coordinates", coordinates)
                .add("creationDate", currentMovie.getCreationDate().toString())
                .add("oscarsCount", currentMovie.getOscarsCount())
                .add("length", currentMovie.getLength())
                .add("genre", currentGenre)
                .add("mpaaRating", currentMovie.getMpaaRating().toString())
                .add("screenwriter", person)
                .build();

        return Json.createObjectBuilder()
                .add("key", key)
                .add("value", movie)
                .build();
    }
}
