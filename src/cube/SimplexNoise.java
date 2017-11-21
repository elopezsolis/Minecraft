package cube;

/**
 * *****************************************************
 * file: SimplexNoise.java 
 * authors: Tomik Ajhajanian, Arineh Abrahamian, Erick Lopez, Jenna Barrett 
 * class: CS 445 Computer Graphics
 * 
 * assignment: Final project Check point 2 
 * date last modified: 11/20/2017
 *
 * purpose: Generates noise given any 2D or 3D coordinate location using 
 * the locally generated frequencies and amplitudes variables.
 * *****************************************************
 */
import java.util.Random;

public class SimplexNoise {
    double[] frequencys;
    double[] amplitudes;
    int largestFeature;
    double persistence;
    int seed;
    SimplexNoiseOctave[] octaves;

    public SimplexNoise(int largestFeature, double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;
        int numberOfOctaves = (int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));
        octaves=new SimplexNoiseOctave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];
        Random rnd = new Random(seed);
        for(int i=0; i<numberOfOctaves; i++){
            octaves[i]=new SimplexNoiseOctave(rnd.nextInt());
            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);
        }

    }
    
    //method: getNoise(int,int)
    //purpose: returns double noise vlaue
    public double getNoise(int x, int y){
        double result=0;
        for(int i=0;i<octaves.length;i++){
          result = result + octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }
        return result;
    }

    //method:getNOise(int, int, int)
    //purpose: returns double noise value 3D
    public double getNoise(int x,int y, int z){
        double result=0;
        for(int i=0;i<octaves.length;i++){
          double frequency = Math.pow(2,i);
          double amplitude = Math.pow(persistence,octaves.length-i);
          result = result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }
        return result;
    }
} 