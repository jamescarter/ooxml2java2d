/*
 * Copyright (C) 2015 James Carter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ooxml2java2d.example.docx.pdf;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.xmlgraphics.java2d.GraphicContext;

import ooxml2java2d.GraphicsBuilder;
import ooxml2java2d.docx.DocxRenderer;

public class DocxToPdfFop {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected a single docx file as an input");
			System.exit(1);
		}

		PdfBuilder builder = new PdfBuilder(new File("output.pdf"));
		new DocxRenderer(new File(args[0])).render(builder);
		builder.finish();
	}

	private static class PdfBuilder implements GraphicsBuilder {
		private File pdf;
		private PDFDocumentGraphics2D g;

		public PdfBuilder(File pdf) {
			this.pdf = pdf;
		}

		@Override
		public Graphics2D nextPage(int pageWidth, int pageHeight) {
			if (g == null) {
				g = new PDFDocumentGraphics2D(false);
				g.setGraphicContext(new GraphicContext());

				try {
					g.setupDocument(new FileOutputStream(pdf), pageWidth, pageHeight);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				g.nextPage(pageWidth, pageHeight);
			}

			return g;
		}

		public void finish() throws IOException {
			g.finish();
		}
	}
}
