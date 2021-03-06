/*
 * bytefrog: a tracing framework for the JVM. For more information
 * see http://code-pulse.com/bytefrog
 *
 * Copyright (C) 2014 Applied Visions - http://securedecisions.avi.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.secdec.bytefrog.clients.common.util

import java.io.File

object FileHelpers {

	implicit class FileHelper(file: File) {

		def getRoot: File = Option { file.getParentFile } match {
			case None => file
			case Some(parent) => parent.getRoot
		}

		def /(pathPart: String) = new File(file, pathPart)

		def lines(implicit codec: scala.io.Codec): TraversableOnce[String] = {
			val source = scala.io.Source.fromFile(file)(codec)
			val base = source.getLines

			new Iterator[String] {
				def hasNext = {
					val h = base.hasNext
					if (!h) source.close
					h
				}
				def next = base.next
			}

		}
	}
}