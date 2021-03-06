/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinariomodelado;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
/**
 *
 * @author Felipe
 */
public class EyesDetector extends Detector
{
    private final CascadeClassifier eyesHaar; // clasificadores
    
    /**
     * Constructor
     */
    public EyesDetector()
    {
        // Inicializamos los clasificadores
        eyesHaar = new CascadeClassifier();
        
        // Inicializamos las propiedades
        scaleFactor = 1.05;
        minNeighbors = 4;
        
        // Cargamos los clasificadores
        loadClassifiers();
    }
    
    /**
     * Cargamos los clasificadores con los que vamos a trabajar
     */
    private void loadClassifiers()
    {   
        // cargamos los clasificadores cascada
        eyesHaar.load(path+"haarcascade_eye.xml");
    }
    
    /**
     * Detector de ojos en un rostro
     * 
     * @param m Matriz original
     * @param grayFrame Matriz original en escala de grises
     * @param faces Matriz de rostros detectados
     */
    public void eyesDetector(Mat m, Mat grayFrame, MatOfRect faces)
    {
        // Matriz para los objetos detectados (ojos)
        MatOfRect eyes = new MatOfRect();
        
        // Convertimos la matriz en un array (vector)
        Rect[] facesArray = faces.toArray();

        // trabajamos en cada uno de los rostros
        for(Rect rect: facesArray){
            // Obtenemos el rectangulo donde vamos a trabajar
            Rect rectCrop = new Rect(rect.x, rect.y , rect.width, rect.height);
            // Obtenemos la matriz roi
            Mat roiGray = grayFrame.submat(rectCrop);
            Mat roiColor = m.submat(rectCrop);
            
            // Obtenemos el tama??o minimo
            minSize = calcSize(roiGray, 0.15F);
            
            eyesHaar.detectMultiScale(roiGray, eyes, scaleFactor, minNeighbors,
                    flags, new Size(minSize, minSize), new Size());

            // Dibujamos los rectangulos para los ojos
            objectDraw(roiColor, eyes, new Scalar(0, 0, 255));
        }
    }
}
