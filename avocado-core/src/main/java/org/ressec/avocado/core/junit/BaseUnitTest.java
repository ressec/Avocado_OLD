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
package org.ressec.avocado.core.junit;

import com.github.javafaker.Faker;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.ressec.avocado.core.exception.checked.FileException;
import org.ressec.avocado.core.helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

/**
 * An abstract class for unit testing that provides some additional functionalities.
 * <hr>
 * <ul>
 * <li>a random generator</li>
 * <li>a faker generator</li>
 * <li>a temporary folder for storing files on the file system</li>
 * </ul>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public abstract class BaseUnitTest
{
    @TempDir
    protected static Path sharedTempDirectory;

    /**
     * Unit test temporary folder for the run.
     */
    protected static String testFolder = System.getProperty("java.io.tmpdir");

    /**
     * System specific file separator character.
     */
    protected static String fileSeparator = System.getProperty("file.separator");

    /**
     * Random number generator.
     */
    protected final Random random = new Random();

    /**
     * Fake Data generator.
     */
    protected final Faker faker = new Faker();

    /**
     * Creates a new base unit test instance.
     */
    protected BaseUnitTest()
    {
        setTestFolder();
    }

    /**
     * Sets the test folder.
     */
    private static void setTestFolder()
    {
        if (!testFolder.endsWith("/"))
        {
            testFolder += File.separator + UUID.randomUUID().toString();
        }
        else
        {
            testFolder += UUID.randomUUID().toString();
        }

        FileHelper.createFileWithDirs(new File(testFolder));
    }

    /**
     * Checks if the given file name exist in the test folder?
     * @param filename File name to check.
     * @return True if the file exist, false otherwise.
     */
    protected static boolean existFile(final @NonNull String filename)
    {
        if (filename.contains(fileSeparator))
        {
            String path = FilenameUtils.getPath(filename);

            if (!normalizeFolderName(path).equals(normalizeFolderName(testFolder)))
            {
                return false;
            }
        }

        try
        {
            if(!FileHelper.getFile(filename).isFile())
            {
                return false;
            }
        }
        catch (FileException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Normalizes the given folder name by removing (if necessary) the leading and training '/' character.
     * @param folderName Folder name to normalize.
     * @return Normalized folder name.
     */
    private static String normalizeFolderName(@NonNull String folderName)
    {
        if (folderName.startsWith(fileSeparator))
        {
            folderName = folderName.substring(1);
        }
        if (folderName.endsWith(fileSeparator))
        {
            folderName = folderName.substring(0, folderName.length() - 1);
        }

        return folderName;
    }

    /**
     * Initializes the test case.
     */
    @BeforeAll
    static void setUpBeforeClass()
    {
        try
        {
            Files.createDirectories(Paths.get(testFolder));
            LOGGER.info(String.format("Test folder set to: [%s]%n", testFolder));
        }
        catch (IOException e)
        {
            LOGGER.error(e);
        }
    }

    /**
     * Finalizes the test case.
     */
    @AfterAll
    public static void tearDownAfterClass()
    {
        try
        {
            FileUtils.deleteDirectory(new File(testFolder));
            LOGGER.info(String.format("Test folder [%s] deleted%n", testFolder));
        }
        catch (IOException e)
        {
            LOGGER.error(e);
        }
    }

    /**
     * Sets up the test case.
     */
    @BeforeEach
    protected void setUpBeforeEach()
    {
        // Empty
    }

    /**
     * Tears down the test case.
     */
    @AfterEach
    protected void tearDownAfterEach()
    {
        // Empty
    }
}