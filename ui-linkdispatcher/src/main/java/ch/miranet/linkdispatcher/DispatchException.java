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

import java.util.List;

public class DispatchException extends Exception {
	private static final long serialVersionUID = 1L;

	public DispatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public DispatchException(List<String> diagnosis) {
		super(flatten(diagnosis));
	}

	private static String flatten(List<String> diagnosis) {
		StringBuffer result = new StringBuffer();
		for (String s : diagnosis) {
			if (result.length() > 0) {
				result.append("\n");
			}
			result.append(s);
		}
		return result.toString();
	}
}
