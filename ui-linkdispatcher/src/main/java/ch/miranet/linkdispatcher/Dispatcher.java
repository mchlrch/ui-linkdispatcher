/*********************************************************************
  This file is part of ui-linkdispatcher,
  see <https://github.com/miraman/ui-linkdispatcher>

  Copyright (C) 2015 Michael Rauch

  ui-linkdispatcher is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  ui-linkdispatcher is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with ui-linkdispatcher.  If not, see <http://www.gnu.org/licenses/>.
*********************************************************************/
package ch.miranet.linkdispatcher;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Using ui-linkdispatcher as Web Browser in Eclipse allows you to
 * open a file by file-url and to browse web-urls in the browser.
 * 
 * - http://www.miranet.ch -> browser
 * - file:///etc/fstab     -> open-file
 */
public abstract class Dispatcher {

	public void dispatch(String link) throws DispatchException {
		Objects.requireNonNull(link, "link");

		try {
			final List<String> diagnosis = new ArrayList<String>();
			final Optional<URI> uri = treatLinkAsUri(link, diagnosis);

			// file-uri
			if (uri.isPresent() && "file".equalsIgnoreCase(uri.get().getScheme())) {
				Optional<File> file = treatUriAsFile(uri.get(), diagnosis);
				openFileOrFail(file, diagnosis);

				// non-file-uri
			} else if (uri.isPresent() && uri.get().isAbsolute()) {
				browse(uri.get());

				// file-path
			} else {
				Optional<File> file = treatLinkAsFile(link, diagnosis);
				openFileOrFail(file, diagnosis);
			}

		} catch (Exception ex) {
			throw new DispatchException("dispatch failed.", ex);
		}
	}

	protected Optional<URI> treatLinkAsUri(String link, List<String> diagnosis) {
		try {
			final URI uri = new URI(link);
			return Optional.of(uri);
		} catch (URISyntaxException uriEx) {
			diagnosis.add(uriEx.getMessage());
			return Optional.empty();
		}
	}

	protected Optional<File> treatLinkAsFile(String link, List<String> diagnosis) {
		try {
			final File file = new File(link);
			return assertThatFileExists(file, diagnosis);

		} catch (Exception ex) {
			diagnosis.add(ex.getMessage());
			return Optional.empty();
		}
	}

	protected Optional<File> treatUriAsFile(URI link, List<String> diagnosis) {
		try {
			final File file = new File(link);
			return assertThatFileExists(file, diagnosis);

		} catch (Exception ex) {
			diagnosis.add(ex.getMessage());
			return Optional.empty();
		}
	}

	protected Optional<File> assertThatFileExists(File file, List<String> diagnosis) {
		if (file.exists()) {
			return Optional.of(file);

		} else {
			diagnosis.add(String.format("File does not exist: %s", file.getPath()));
			return Optional.empty();
		}
	}

	protected void openFileOrFail(Optional<File> file, List<String> diagnosis) throws DispatchException, IOException {
		if (file.isPresent()) {
			open(file.get());
		} else {
			throw new DispatchException(diagnosis);
		}
	}

	protected abstract void browse(URI uri) throws IOException;

	protected abstract void open(File file) throws IOException;

}
