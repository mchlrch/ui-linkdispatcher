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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class LinkHandler {

	private static final String xdgOpenCmd = "xdg-open";

	private static enum BrowseHandler {
		Desktop, Xdg
	}

	private static enum OpenHandler {
		Desktop, Xdg
	}

	private final BrowseHandler browseHandler;
	private final OpenHandler openHandler;

	LinkHandler() {
		browseHandler = initBrowseHandler();
		openHandler = initOpenHandler();
	}

	boolean canBrowse() {
		return browseHandler != null;
	}

	boolean canOpen() {
		return openHandler != null;
	}

	void browse(URI uri) throws IOException {
		switch (browseHandler) {
		case Desktop:
			Desktop.getDesktop().browse(uri);
			break;
		case Xdg:
			invokeXdgOpen(uri.toASCIIString());
			break;
		default:
			throw new UnsupportedOperationException("browse not supported.");
		}
	}

	void open(File file) throws IOException {
		switch (openHandler) {
		case Desktop:
			Desktop.getDesktop().open(file);
			break;
		case Xdg:
			invokeXdgOpen(file.getAbsolutePath());
			break;
		default:
			throw new UnsupportedOperationException("open not supported.");
		}
	}

	private void invokeXdgOpen(String argument) {
		try {
			final String cmd = xdgOpenCmd + " " + argument;

			System.out.println("Invoke: " + cmd);

			Runtime.getRuntime().exec(cmd);

		} catch (IOException ioex) {
			throw new RuntimeException(ioex);
		}
	}

	private BrowseHandler initBrowseHandler() {
		if (Desktop.isDesktopSupported()
				&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			return BrowseHandler.Desktop;

		} else {
			try {
				Runtime.getRuntime().exec(xdgOpenCmd);
				return BrowseHandler.Xdg;
			} catch (IOException ioex) {
				// xdg-open not found
			}
		}
		return null;
	}

	private OpenHandler initOpenHandler() {
		if (Desktop.isDesktopSupported()
				&& Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
			return OpenHandler.Desktop;

		} else {
			try {
				Runtime.getRuntime().exec(xdgOpenCmd);
				return OpenHandler.Xdg;
			} catch (IOException ioex) {
				// xdg-open not found
			}
		}
		return null;
	}
}
