/*
 * Copyright(c) 2021 by Resse Christophe.
 * --------------------------------------------------------------------------------------
 * This file is part of Resse Christophe public projects which is licensed
 * under the Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 *
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * --------------------------------------------------------------------------------------
 */
package org.ressec.avocado.core.test.file;

import lombok.NonNull;
import org.junit.jupiter.api.*;
import org.ressec.avocado.core.exception.checked.FileException;
import org.ressec.avocado.core.helper.FileHelper;
import org.ressec.avocado.core.junit.BaseUnitTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A test class for unit testing the {@link FileHelper} services.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
final class TestFileHelper extends BaseUnitTest
{
    /**
     * Sets up the test case.
     */
    @BeforeEach
    public final void setUpBeforeEach()
    {
        // Empty
    }

    /**
     * Tears down the test case.
     */
    @AfterEach
    public final void tearDownAfterEach()
    {
        // Empty
    }

    /**
     * Dumps the content of a file.
     * @param filename File name.
     * @param file File.
     * @throws Exception Thrown in case an error occurred trying to dump the content of the file.
     */
    private static void dumpFile(final @NonNull String filename, final @NonNull File file) throws Exception
    {
        System.out.println("File name is: ");
        System.out.println(filename + "\n");

        System.out.println("URL of file is: ");
        System.out.println(file.toURI().toURL() + "\n");

        System.out.println("File content is: ");
        Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(System.out::println);

    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file loaded on the classpath.
     */
    @Test
    final void testFileHelperLoadFileFromClasspath()
    {
        String filename = "/log4j2-test.properties";

        try
        {
            String content = FileHelper.loadFileContentAsString(filename);
            Assertions.assertNotNull(content);

            System.out.println(content);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file retrieved on the class path.
     */
    @Test
    final void testFileHelperGetFileFromClasspath()
    {
        String filename = "/log4j2-test.properties";

        try
        {
            File file = FileHelper.getFile(filename);
            Assertions.assertNotNull(file);

            dumpFile(filename, file);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file located in a jar file on the class path.
     */
    @Test
    final void testFileHelperGetFileFromJar()
    {
        String filename = "jar:file:/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "/changelog.txt";
        filename = "/META-INF/MANIFEST.MF";

        try
        {
            File file = FileHelper.getFile(filename);
            Assertions.assertNotNull(file);

            dumpFile(filename, file);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file located in a jar file on the class path.
     */
    @Test
    final void testFileHelperLoadFileFromJar()
    {
        String filename;
        filename = "/META-INF/MANIFEST.MF"; // Not unique, exist for many JAR files, first one is returned
        filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "/changelog.txt"; // Should be the changelog from lombok jar file.

        try
        {
            String content = FileHelper.loadFileContentAsString(filename);
            Assertions.assertNotNull(content);

            System.out.println(content);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from an http url.
     */
    @Test()
    @Timeout(30000) // 30 secs timeout
    final void testFileHelperGetFileFromHttpUrl()
    {
        String filename = "https://www.w3.org/TR/PNG/iso_8859-1.txt";

        try
        {
            File file = FileHelper.getFile(filename);
            Assertions.assertNotNull(file);

            dumpFile(filename, file);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from an http url.
     */
    @Test()
    @Timeout(30000) // 30 secs timeout
    final void testFileHelperLoadFileFromHttpUrl()
    {
        String filename = "https://www.w3.org/TR/PNG/iso_8859-1.txt";

        try
        {
            String content = FileHelper.loadFileContentAsString(filename);
            Assertions.assertNotNull(content);

            System.out.println(content);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from a file url.
     */
    @Test
    final void testFileHelperGetFileFromFileUrl()
    {
        // This test will fail if run on remote machine such as GitLab or Travis!
        String filename = "file:/Volumes/Technology/project/gitlab.com/hemajoo/foundation/hemajoo-foundation/foundation-utility/target/test-classes/log4j2-test.properties";
        filename = "file:../etc/deploy-settings.xml";

        try
        {
            File file = FileHelper.getFile(filename);
            Assertions.assertNotNull(file);

            dumpFile(filename, file);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from a file url.
     */
    @Test
    final void testFileHelperLoadFileFromFileUrl()
    {
        // This test will fail if run on remote machine such as GitLab or Travis!
        String filename = "file:/Volumes/Technology/project/gitlab.com/hemajoo/foundation/hemajoo-foundation/foundation-utility/target/test-classes/log4j2-test.properties";
        filename = "file:../etc/deploy-settings.xml";

        try
        {
            String content = FileHelper.loadFileContentAsString(filename);
            Assertions.assertNotNull(content);

            System.out.println(content);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from a jar file url.
     */
    @Test
    final void testFileHelperLoadFileFromJarUrl()
    {
        String filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "jar:file:./src/test/resources/google-auth-library-credentials-0.16.1.jar!/META-INF/MANIFEST.MF";
        try
        {
            File file = FileHelper.getFile(filename);
            Assertions.assertNotNull(file);

            dumpFile(filename, file);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from a jar file url.
     */
    @Test
    final void testFileHelperGetFileFromJarUrl()
    {
        String filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "jar:file:./src/test/resources/google-auth-library-credentials-0.16.1.jar!/META-INF/MANIFEST.MF";

        try
        {
            String content = FileHelper.loadFileContentAsString(filename);
            Assertions.assertNotNull(content);

            System.out.println(content);
        }
        catch (Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test the ability to load a file using its file system complete path and name.
     */
    @Test
    final void testFileHelperGetFileFromFileSystem()
    {
        try
        {
            Path path = Files.createTempFile("test_temp_file", ".tjf");
            File file = path.toFile();
            file.deleteOnExit();
            String content = FileHelper.loadFileContentAsString(file.getPath());

            Assertions.assertNotNull(content);
        }
        catch (IOException | FileException e)
        {
            Assertions.fail(e.getMessage());
        }
    }
}