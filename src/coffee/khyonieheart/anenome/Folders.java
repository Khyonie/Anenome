package coffee.khyonieheart.anenome;

import java.io.File;
import java.util.Objects;

public class Folders
{
	@NotNull
	public static File ensureFolder(
		@NotNull File file
	) throws IllegalStateException
	{
		Objects.requireNonNull(file);

		if (file.exists())
		{
			return file;
		}

		if (!file.mkdirs())
		{
			throw new IllegalStateException("Could not create folder " + file.getAbsolutePath());
		}

		return file;
	}

	@NotNull
	public static File ensureFolder(
		@NotNull String filepath
	) throws IllegalStateException
	{
		Objects.requireNonNull(filepath);
		File file = new File(filepath);

		return ensureFolder(file);
	}
}
