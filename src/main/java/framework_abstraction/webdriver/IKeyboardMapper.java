package framework_abstraction.webdriver;

import org.openqa.selenium.Keys;

/**
 Interface for mapping keyboard strokes.
*/
public interface IKeyboardMapper
{
	/**
	 Maps a keyboard key to type <see cref="string"/>.
	 
	 @param key The key to map.
	 @return The mapped key.
	*/
	String Map(Keys key);
}
