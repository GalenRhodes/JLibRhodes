package com.projectgalen.jlib.work;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: XMLResourceBundleControl.java
 *     PACKAGE: com.projectgalen.jlib.work
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 05/20/2019
 * DESCRIPTION:
 *
 * Copyright © 2019 Project Galen. All rights reserved.
 *
 * "It can hardly be a coincidence that no language on Earth has ever produced the expression 'As pretty as an airport.' Airports
 * are ugly. Some are very ugly. Some attain a degree of ugliness that can only be the result of special effort."
 * - Douglas Adams from "The Long Dark Tea-Time of the Soul"
 *
 * Permission to use, copy, modify, and distribute this software for any purpose with or without fee is hereby granted, provided
 * that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * ============================================================================================================================== */

import com.projectgalen.jlib.Q;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("HardCodedStringLiteral")
public class XMLResourceBundleControl extends ResourceBundle.Control {

    private static final String       XML            = "xml";
    private static final List<String> SINGLETON_LIST = Collections.singletonList(XML);

    @Override
    public List<String> getFormats(String baseName) {
        return SINGLETON_LIST;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        if(Q.Z(baseName) || Q.Z(locale) || Q.Z(format) || Q.Z(loader)) throw new IllegalArgumentException("BaseName, locale, format nor loader can be null.");
        if(!format.equals(XML)) throw new IllegalArgumentException("Format must be XML.");

        String    bundleName   = toBundleName(baseName, locale);
        String    resourceName = toResourceName(bundleName, format);
        final URL url          = loader.getResource(resourceName);
        if(Q.Z(url)) return null;

        final URLConnection urlconnection = url.openConnection();
        if(Q.Z(urlconnection)) return null;
        if(reload) urlconnection.setUseCaches(false);

        try(final InputStream stream = urlconnection.getInputStream(); final BufferedInputStream bis = new BufferedInputStream(stream);) {
            return new XMLResourceBundle(bis);
        }
    }

}
