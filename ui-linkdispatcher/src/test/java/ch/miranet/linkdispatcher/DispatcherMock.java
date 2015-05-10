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
import java.util.Objects;

public class DispatcherMock extends Dispatcher {

	public int browseCount = 0;
	public int openCount = 0;

	public boolean failInOpenAndBrowse = false;

	public boolean browseCalledExactlyOnce() {
		return browseCount == 1;
	}

	public boolean openCalledExactlyOnce() {
		return openCount == 1;
	}

	@Override
	protected void browse(URI uri) throws IOException {
		Objects.requireNonNull(uri, "uri");

		browseCount++;

		if (failInOpenAndBrowse) {
			throw new RuntimeException("DispatcherMock.browse() failed intentionally.");
		}
	}

	@Override
	protected void open(File file) throws IOException {
		Objects.requireNonNull(file, "file");

		openCount++;

		if (failInOpenAndBrowse) {
			throw new RuntimeException("DispatcherMock.open() failed intentionally.");
		}
	}

}
