package org.java.mentorship.validation;


import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SongValidatorTest {

   private SongValidator validator = new SongValidator();

   @Test
   public void validateShouldThrowWhenNameIsNull(){
      Song song = new Song(1,"Fei1n","Rap",2,1,1);

      song.setName(null);
      FieldIsNullException exception = Assertions.assertThrows(FieldIsNullException.class, () -> validator.validate(song));
      Assertions.assertEquals("Field 'name' is null", exception.getMessage(), "unexpected exception message");

   }

//   @Test
//   public void validateShouldDoNothingWhenNameIsNotNull(){
//      Song song = new Song();
//
//      song.setName("lalalla");
//      Assertions.assertDoesNotThrow(() -> validator.validate(song),"expected exception to be thrown");
//
//   }

   @Test
   public void validateShouldThrowWhenStyleIsNull(){
      Song song = new Song(1,"Fei1n","Rap",2,1,1);

      song.setStyle(null);
      FieldIsNullException exception = Assertions.assertThrows(FieldIsNullException.class, () -> validator.validate(song));
      Assertions.assertEquals("Field 'style' is null", exception.getMessage(), "unexpected exception message");

   }

   @Test
   public void validateShouldThrowWhenDurationIsNull(){
      Song song = new Song(1,"Fei1n","Rap",2,1,1);

      song.setDuration(null);
      FieldIsNullException exception = Assertions.assertThrows(FieldIsNullException.class, () -> validator.validate(song));
      Assertions.assertEquals("Field 'duration' is null", exception.getMessage(), "unexpected exception message");

   }
   @Test
   public void validateShouldThrowWhenArtistIDIsNull(){
      Song song = new Song(1,"Fei1n","Rap",2,1,1);

      song.setArtistId(null);
      FieldIsNullException exception = Assertions.assertThrows(FieldIsNullException.class, () -> validator.validate(song));
      Assertions.assertEquals("Field 'artistId' is null", exception.getMessage(), "unexpected exception message");

   }
   @Test
   public void validateShouldThrowWhenAlbumIDIsNull(){
      Song song = new Song(1,"Fei1n","Rap",2,1,1);

      song.setAlbumId(null);
      FieldIsNullException exception = Assertions.assertThrows(FieldIsNullException.class, () -> validator.validate(song));
      Assertions.assertEquals("Field 'albumId' is null", exception.getMessage(), "unexpected exception message");

   }

}
