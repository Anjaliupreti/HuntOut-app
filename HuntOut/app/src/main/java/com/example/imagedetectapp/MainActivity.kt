package com.example.imagedetectapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager.LayoutParams.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.imagedetectapp.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {



    //creating variable for image view

    lateinit var  imageView: ImageView

    //Declaring the variable bitmap for storing the image

    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //Initializing the variables

        imageView=findViewById(R.id.imageView)

        //Dialogue box 1

        val builder = AlertDialog.Builder(this)

        builder.setTitle("WELCOME TO HuntOut")

        builder.setMessage("An app that predict what animal it is.")

        builder.show()

        val fileName="label.txt"

        val inputString=application.assets.open(fileName).bufferedReader().use { it.readText() }

        //List of type string and we will load label.txt using the function application.assets.open(filename) and
        //we will read it using the function bufferedReader()

        val townList=inputString.split("\n")

        var tv:TextView=findViewById(R.id.textView)

        //FOR SELECT BUTTON

        var select:Button=findViewById(R.id.button)

        select.setOnClickListener(View.OnClickListener {

            var intent:Intent=Intent(Intent.ACTION_GET_CONTENT)

            intent.type="image/*"

            startActivityForResult(intent,100)
        })

        //FOR ABOUT APP BUTTON (on clicking it, a dialogue box about app will appear)

        var aboutapp:Button=findViewById(R.id.aboutapp)

        aboutapp.setOnClickListener(View.OnClickListener {

            //Dialogue Box 2

            val builder = AlertDialog.Builder(this)

            builder.setTitle("About App")

            builder.setMessage("Animals serve as our friends, employees, eyes and ears, and nourishment." +
                    "The significance of animals cannot be overstated. Our existence would be impossible without them." +
                    "We bring out to you 'HuntOut', an app which will help you know our animal kingdom better." +
                    "You can easily know about  the animals around you by uploading their pictures. " +
                    "Animals are a gift from above for they truly define the words/nUNCONDITIONAL LOVE.")

            builder.show()
        }

        )


        //FOR PREDICT BUTTON

        var predict:Button=findViewById(R.id.button2)

        predict.setOnClickListener(View.OnClickListener {

            //Resizing the bitmap

            var resized:Bitmap=Bitmap.createScaledBitmap(bitmap,224,224,true)

            val model = MobilenetV110224Quant.newInstance(this)

            // Creates inputs for reference.

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)

            //Creating byteBuffer image from resized bitmap

            var tbuffer=TensorImage.fromBitmap(resized)

            var byteBuffer=tbuffer.buffer

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.

            val outputs = model.process(inputFeature0)

            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max=getMax(outputFeature0.floatArray)

            tv.setText(townList[max])

            // Releases model resources if no longer used.

            model.close()
        })

        //To redirect user to google search

        var moreinformation:TextView = findViewById(R.id.moreinfo)

        moreinformation.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${tv.text}"))

            startActivity(intent)
        }

    }

    //Over-riding the function

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Once the user selects the image changing the imageView to the selected image

        imageView.setImageURI(data?.data)

        //Storing the image to bitmap so that we can predict.
        //We are using MediaStore and getting the bitmap from uri which is stored in the data variable.

        var uri: Uri?= data?.data

        bitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
    }

    //Function to extract maximum value index

    fun getMax(arr: FloatArray):Int{

        var ind=0

        var min=0.0f        //to convert to float

        for(i in 0..1000){

            if (arr[i]>min){                 //Check if arr at 0 index has the value of highest probability
                //greater than 0 then it will give the index ind=i.
                ind=i                        // At last ind will be the index at highest value

                min= arr[i]

            }
        }
        return  ind
    }
}