package org.hadoop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Process;
import java.lang.Runtime;
import java.util.HashMap;

/**
 * SystemStatusReader is a collection of methods to read system status (cpu and memory)
 * 
 * @author Andreu Correa Casablanca
 */
public class SystemStatusReader
{
    public static final int CONSERVATIVE    = 0;
    public static final int AVERAGE     = 1;
    public static final int OPTIMISTIC  = 2;

    /**
     * cpuUsage gives us the percentage of cpu usage
     * 
     * mpstat -P ALL out stream example:
     *
     *  Linux 3.2.0-30-generic (castarco-laptop)    10/09/12    _x86_64_    (2 CPU)                 - To discard
     *                                                                                              - To discard
     *  00:16:30     CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest   %idle    - To discard
     *  00:16:30     all   17,62    0,03    3,55    0,84    0,00    0,03    0,00    0,00   77,93
     *  00:16:30       0   17,36    0,05    3,61    0,83    0,00    0,05    0,00    0,00   78,12
     *  00:16:30       1   17,88    0,02    3,49    0,86    0,00    0,01    0,00    0,00   77,74
     * 
     * @param measureMode Indicates if we want optimistic, convervative or average measurements.
     */
    public static Double cpuUsage (int measureMode) throws Exception {

        BufferedReader mpstatReader = null;

        String      mpstatLine;
        String[]    mpstatChunkedLine;

        Double      selected_idle;

        try {
            Runtime runtime = Runtime.getRuntime();
            Process mpstatProcess = runtime.exec("mpstat -P ALL");

            mpstatReader = new BufferedReader(new InputStreamReader(mpstatProcess.getInputStream()));

            // We discard the three first lines
            mpstatReader.readLine();
            mpstatReader.readLine();
            mpstatReader.readLine();

            mpstatLine = mpstatReader.readLine();
            if (mpstatLine == null) {
                throw new Exception("mpstat didn't work well");
            } else if (measureMode == SystemStatusReader.AVERAGE) {
                mpstatChunkedLine = mpstatLine.replaceAll(",", ".").split("\\s+");
                selected_idle = Double.parseDouble(mpstatChunkedLine[10]);
            } else {
                selected_idle   = (measureMode == SystemStatusReader.CONSERVATIVE)?200.:0.;
                Double candidate_idle;

                int i = 0;
                while((mpstatLine = mpstatReader.readLine()) != null) {
                    mpstatChunkedLine = mpstatLine.replaceAll(",", ".").split("\\s+");
                    candidate_idle = Double.parseDouble(mpstatChunkedLine[10]);

                    if (measureMode == SystemStatusReader.CONSERVATIVE) {
                        selected_idle = (selected_idle < candidate_idle)?selected_idle:candidate_idle;
                    } else if (measureMode == SystemStatusReader.OPTIMISTIC) {
                        selected_idle = (selected_idle > candidate_idle)?selected_idle:candidate_idle;
                    }
                    ++i;
                }
                if (i == 0) {
                    throw new Exception("mpstat didn't work well");
                }
            }
        } catch (Exception e) {
            throw e; // It's not desirable to handle the exception here
        } finally {
            if (mpstatReader != null) try {
                mpstatReader.close();
            } catch (IOException e) {
                // Do nothing
            }
        }

        return  100-selected_idle;
    }

    /**
     * memoryUsage gives us data about memory usage (RAM and SWAP)
     */
    public static HashMap<String, Integer> memoryUsage () throws Exception {
        BufferedReader freeReader = null;

        String      freeLine;
        String[]    freeChunkedLine;

        HashMap<String, Integer> usageData = new HashMap<String, Integer>();

        try {
            Runtime runtime = Runtime.getRuntime();
            Process freeProcess = runtime.exec("free -k"); // We measure memory in kilobytes to obtain a greater granularity

            freeReader = new BufferedReader(new InputStreamReader(freeProcess.getInputStream()));

            // We discard the first line
            freeReader.readLine();

            freeLine = freeReader.readLine();
            if (freeLine == null) {
                throw new Exception("free didn't work well");
            }
            freeChunkedLine = freeLine.split("\\s+");

            usageData.put("total", Integer.parseInt(freeChunkedLine[1]));

            freeLine = freeReader.readLine();
            if (freeLine == null) {
                throw new Exception("free didn't work well");
            }
            freeChunkedLine = freeLine.split("\\s+");

            usageData.put("used", Integer.parseInt(freeChunkedLine[2]));

            freeLine = freeReader.readLine();
            if (freeLine == null) {
                throw new Exception("free didn't work well");
            }
            freeChunkedLine = freeLine.split("\\s+");

            usageData.put("swap_total", Integer.parseInt(freeChunkedLine[1]));
            usageData.put("swap_used", Integer.parseInt(freeChunkedLine[2]));
        } catch (Exception e) {
            throw e;
        } finally {
            if (freeReader != null) try {
                freeReader.close();
            } catch (IOException e) {
                // Do nothing
            }
        }

        return usageData;
    }
}
