package restapi.webapp;

import java.time.LocalDateTime;
import java.util.Objects;

public class SampleClass {
    String data;
    LocalDateTime timestamp;

    public SampleClass(String data, LocalDateTime timestamp) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return super.toString(); // by default, returns the address of the object in memory
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); // by default, checks equality between addresses
    }

    /*
    take a look at our Person class for example of Horner's method/scheme
    HashCode is implemented in order to enable fast lookup when using data structures
    with hash tables
    4 bytes-> 32 bit
    hashcode logic can be different from one class to another (programmer's logic),
    if using code templates, it can change from one Java version to another
    2^32 binary strings -> 4B hashcodes.
    chance of collisions is high (birthday paradox)

    There is a difference between hashCode function to a cryptographic hash function H(x)
     */
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }


    @Override
    public int hashCode() {
        return Objects.hash(getData(), getTimestamp());
    }
}
