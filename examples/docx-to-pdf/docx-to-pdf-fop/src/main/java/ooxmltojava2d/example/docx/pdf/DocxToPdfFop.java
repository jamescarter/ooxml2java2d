package ooxmltojava2d.example.docx.pdf;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.xmlgraphics.java2d.GraphicContext;

import ooxmltojava2d.docx.DocxToGraphics2D;
import ooxmltojava2d.docx.GraphicsBuilder;

public class DocxToPdfFop {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected a single docx file as an input");
			System.exit(1);
		}

		PdfBuilder builder = new PdfBuilder(new File("output.pdf"));
		new DocxToGraphics2D(builder, new File(args[0])).process();
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
