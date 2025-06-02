package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Grew_50_percent


//Создаем текстовое поле для ввода переменных
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextFilledGiraffe (
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,

    ){  val interactionSource = remember { MutableInteractionSource() }

    // параметры ниже будут переданы в BasicTextField для правильного поведения текстового поля,
    // и в поле оформления для правильного стиля и размера
    val enabled = true
    val singleLine = true

    val colors = TextFieldDefaults.colors(
        unfocusedContainerColor = colorResource(R.color.Lavanda),
        unfocusedTextColor = colorResource(R.color.Grew),
        focusedContainerColor  = colorResource(R.color.white),// цвет при получении фокуса
        focusedTextColor = colorResource(R.color.DarkGrew) // цвет при отсутствии фокуса
    )

    var myBoxVisualTextFieldModifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .background(colorResource(R.color.white))
        .border(
            width = 2.dp,
            color = colorResource(R.color.white),
            shape = RoundedCornerShape(5.dp)
        )

    Box(myBoxVisualTextFieldModifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .size(height = 30.dp, width = 500.dp),

            textStyle = TextStyle (fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = DarkGrew),
            //внутренняя реализация BasicTextField будет отправлять события фокуса
            interactionSource = interactionSource, //Помогает определить находится ли данное поле в фокусе или нет
            enabled = enabled, //управляет включенным состоянием TextField. Когда false, визуально текстовое поле
            // будет отображаться в отключенном состоянии пользовательского интерфейса. Вы также должны
            // передать то же значение в , BasicTextFieldчтобы он соответствующим образом отрегулировал
            // поведение, сделав текстовое поле нередактируемым, нефокусируемым и невыбираемым
            singleLine = singleLine, //указывает, является ли это однострочным или многострочным текстовым полем.
            // Вы должны передать то же значение, что и BasicTextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) //Выбор типа клавиатуры
        ){
            TextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = it, //поле ввода текста, которое оборачивает этот декоративный блок.
                // Вы передадите сюда управляемый фреймворком компонуемый параметр "innerTextField" из decorationBox lambda BasicTextField
                singleLine = singleLine, //указывает, является ли это однострочным или многострочным текстовым полем.
                // Вы должны передать то же значение, что иBasicTextField
                enabled = enabled, //управляет включенным состоянием OutlinedTextField. Когда false,
                // визуально текстовое поле будет отображаться в отключенном состоянии пользовательского интерфейса.
                // Вы также должны передать то же значение в, BasicTextField что бы он соответствующим образом отрегулировал поведение,
                // сделав текстовое поле нередактируемым, нефокусируемым и невыбираемым
                interactionSource = interactionSource, //это только для чтения, InteractionSource gредставляющее поток Interactions
                // для этого текстового поля. Сначала вы создаете и передаете свой собственный remember MutableInteractionSource,
                // чтобы BasicTextField  мог отправлять события. А затем передаете тот же экземпляр в этот декоративный блок DecorationBox,
                // чтобы он мог наблюдать за Interactions и настраивать внешний вид / поведение в разных Interactions.

                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(start = 5.dp, top = 2.dp, end = 5.dp, bottom = 2.dp),
                // значения интервала для применения внутри между внутренними элементами текстового поля и контейнером декоративных блоков.
                colors = colors, //Настройка цвета которая будет использоваться для определения цвета текста и содержимого
                // (включая метку, заполнитель, начальные и конечные значки, границу) для этого текстового поля в разных состояниях.
                placeholder ={Text(placeholder, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Grew_50_percent))
                } //временный текст, который отображается внутри поля.
            )
        }
    }





}

@Preview
@Composable
fun BasicTextFilledGiraffePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicTextFilledGiraffe(
            placeholder = "Value",
            onValueChange = { },
            modifier = Modifier,
            value = ""
        )
    }
}