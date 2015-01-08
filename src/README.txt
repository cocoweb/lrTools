LoadRunner

一、导入导出参数文件（excel<-->txt/dat）
二、导入导出测试用例（excel<-->txt/dat）
三、实现LoadRunner的参数获取与HTTP请求获取
1、实现参数存储与提取
2、实现HTTP请求
3、实现HTTP响应的获取
四、构建响应平台


语法：
Int web_custom_request (const char *RequestName, <List of Attributes>,

  [EXTRARES, <List of Resource Attributes>,] LAST );

返回值
返回LR_PASS（0）代表成功，LR_FAIL（1）代表失败。

参数：
RequestName：步骤的名称，VuGen中树形视图中显示的名称。

List of Attribute：支持的属性有以下几种：

1.      URL：页面地址。

2.      Method ：页面的提交方式，POST或GET。

3.      TargetFrame：包含当前链接或资源的frame的名称。参见List of Attributes的同名参数。

4.      EncType：编码类型。

5.      RecContentType：响应头的内容类型。参见List of Attributes的同名参数。

6.      Referer：参见List of Attributes的同名参数。

7.      Body：请求体。参见List of Attributes的同名参数。

8.      RAW BODY：参见List of Attributes的同名参数。

9.      BodyFilePath：作为请求体传送的文件的路径。它不能与下面的属性一起使用：Body，或者其他Body属性或Raw Body属性包括BodyBinary，BodyUnicode， RAW_BODY_START或Binary=1。

10.  Resource、ResourceByteLimit、Snapshot、Mode：参见List of Attributes的同名参数。

11.  ExtraResBaseDir：参见List of Attributes的同名参数。

12.  UserAgent：用户代理，它是一个HTTP头的名字，用来标识应用程序，通常是浏览器，它呈现的是用户和服务器的交互。

例如：头信息“User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)”识别的是Window NT下的IE浏览器6.0。其他的User-Agent的值用来描述其他的浏览器，或者非浏览器程序。通常，一个应用程序中所有的请求都使用相同的用户代理，录制者作为一个运行时参数来指定（Run-Time Setting—Browser Emulation—User Agent）。不管怎么说，即使是在一个简单的浏览器进程中，仍有可能会用到直接与服务器交互的非浏览器组件（例如ActiveX控件），通常他们有着不同于浏览器的用户代理属性。指定“UserAgent”表示这是一个非浏览器的请求。指定的字符串被HTTP头“User-Agent:” 使用，在某些情况下，它同时会影响回放脚本时的行为。例如，不使用浏览器缓存，假设指定的URL属于资源等等。

LoadRunner本身不检查指定的字符串与浏览器本身的值是否相同。

13.  Binary：“Binary=1”表示页面请求体中的每一个以file://x/##形式出现的值（在这里“##”代表2个十六进制数字），都会被替换为单字节的十六进制的值。

如果“Binary=0”（默认值），所有的字符序列只是按照字面的值传递。

需要注意双斜杠的用法。在C编译器中双斜杠被解释为单斜杠。如果不需要零字节，单斜杠可以在Binary不等于1的情况下使用（例如，使用\x20代替file://x20/）。如果需要零字节，那么只能使用file://x00/且设置 “Binary=1”，\x00在逻辑上会被截断。

14.  ContentEncoding

指定请求体的使用指定的方式（gzip或者deflate）进行编码（例如，压缩），相应的“Content-Encoding:” HTTP头会和此请求一起发送。这个参数适用于web_custom_request和web_submit_data。

EXTRARES：表明下面的参数将会是List Of Resource Attributes了。 

LAST ：结尾的标示符。

说明
允许使用任何方法和请求体创建自定义的HTTP请求。默认情况下,当VuGen无法使用其他函数解释用户请求时,会生成此函数。

使用Add对话框（在脚本环境下,右击鼠标,会出现快捷菜单），可以手工插入此函数。要指定特殊的HTTP头信息,需要增加web_add_header或者web_add_auto_header函数。

RecContentType：参见List of Attributes的同名参数。

EncType：编码类型。此参数给出一个内容类型（Content-Type），指定其做为回放脚本时“Content-Type”请求头的值，例如 “text/html”。Web_custom_request函数不处理未编码的请求体。请求体参数将会使用已经指定的编码方式。因此，如果指定了不匹配HTTP请求体的“EncType”,会引发服务端的错误。通常我们建议不要手动修改录制时的“EncType”值。

任何对于“EncType”的指定都会覆盖web_add_[auto_]header函数指定的Content-Type。当指定了“EncType=” （空值）时，不会产生“Content-Type” 请求头。当省略了“EncType”时，任何一个web_add_[auto_]header函数都会起作用。如果既没有指定EncType也没有web_add_[auto_]header函数，且“Method=POST”，且“Method=POST”，“application/x-www-form-urlencoded”会做为默认值来使用。其他情况下，不会产生Content-Type请求头。

仅仅当Recording Options--Recording --HTML-based script-- Record within the current script step选项被选中时，List of Resource Attributes才会被插入到代码中。

所有的Web Vusers ，运行在HTTP模式或者Wireless Session Protocol (WSP) 回放模式下的WAP Vusers都支持web_custom_request函数。

Using Binary Code

可以使用下面的格式在web_custom_request 函数的“Body”属性中加入二进制字符串。

\x[char1][char2]

通过2个字符[char1][char2]来表示十六进制的值。

例如：\x24表示16*2+4=36，它代表“$”符号，\x2B代表的是“+”符号。

如果不足2个字符，也不是有效的十六进制字符，VuGen则会当作ASCII文本处理。所以要注意，对于不足2个字符的十六进制，要在前面补0。例如 “\x2”是无效的十六进制串，需要边为“\x02”。

注意二进制的值是以“\\x” 的形式出现在脚本中的，也就是说在“x” 前面有2个反斜杠。这是由C语言的转义语法决定的。但是，当使用VuGen生成web_custom_request 函数时，只需要输入一个反斜杠。

如果在web_custom_request 中使用参数化，定义参数时只需要包含一个反斜杠这是由于参数替换时是不考虑C的语法转换的。

List of Attributes
FtpAscii：“1”使用ASCII模式处理FTP操作；"0" 使用二进制模式。

TargetFrame： 当前链接或资源所在Frame的名称。除了Frame的名字，还可以指定下面的参数：

_BLANK：打开一个空窗口。

_PARENT：把最新更改过的的Frame替换为它的上级。

_SELF：替换最新更改过的的Frame。

_TOP：替换整个页面。

RecContentType：录制脚本时响应头的内容类型。例如text/html、 application/x-javascript等。当没有设置Resource属性时，用它来确定目标URL是否是可记录的资源。此属性包含主要的和次要的资源。最频繁使用的类型是 text、application、image。次要的类型根据资源不同变化很多。例如："RecContentType=text/html"：表示html文本。"RecContentType=application/msword"：表示当前使用的是Msword。

Referer: 当前页面关联的页面。如果已经显式指定了url的地址，此项可以省略。

Resource：指示URL是否属于资源。1 是；0 不是。设置了这个参数后，RecContentType参数被忽略。“Resource=1”，意味着当前操作与所在脚本的成功与否关系不大。在下载资源时如果发生错误，是当作警告而不是错误来处理的；URL是否被下载受“Run-Time Setting—Browser Emulation--Download non-HTML resources” 这个选项的影响。此操作的响应信息是不做为HTML来解析的。“Resource=0”，表明此URL是重要的，不受发送请求（RTS）的影响，在需要时也会解析它。

ResourceByteLimit：web页面下载资源的极限大小。当达到设置的极限后，无法下载其他资源。仅仅对需要下载的资源有效。

下载过程：如果总计下载大小小于极限值，则正常开始下载。如果当下载时达到了设置的极限值，资源大小可知（在HTTP响应头中指定了Content-Length），这中情况下，如果只需要一个缓冲区，那么下载可以正常完成。如果需要的不止一个缓冲区，或者资源大小不可知，下载就会中断同时关闭当前连接。

这个特性可以用来模拟用户不等待一个页面下载完成时导航到另一个页面的情况。

ResourceByteLimit 在HTTP模式中无法使用，在Concurrent Groups（Vuser脚本中的一个区，此区中的所有函数并发执行）区中也无法使用。仅仅适用于Sockets的回放，WinInet也是不适用的。

Snapshot：快照的文件名，关联时使用。

Mode：两种录制级别HTML、HTTP。

HTML级别：在当前Web界面上录制直观的HTML动作。以一步步的web_url、web_link、web_image、web_submit_form来录制这些动作。VuGen仅仅录制返回HTML页面的请求，不处理脚本和应用程序。

HTTP级别：VuGen把所有的请求录制为web_url指令，不生成web_link、web_image、web_submit_form这些函数。这种方法更为灵活，但是生成的脚本不够直观。

ExtraResBaseDir（目前仅适用与web_custom_request函数）：根URL，放在EXTRARES组里。它是用来解析相对URL的（译者加：类似于Windows的相对路径和绝对路径）。

URL可以是绝对路径（例如http://weather.abc.com/weather/forecast.jsp?locCode=LFPO），也可以是相对路径（例如“forecast.jsp?locCode=LFPO”）。

真正的URL的下载是通过绝对路径进行的，所以相对URL路径必须使用根路径URL去解析。例如，使用http://weather.abc.com/weather/做为根路径来解析“forecast.jsp?locCode=LFPO”，最后的URL是：http://weather.abc.com/weather/forecast.jsp?locCode=LFPO。如果没有指定“ExtraResBaseDir”，默认的根URL是主页面的URL。

Body（目前仅适用与web_custom_request函数）：请求体。不同的应用中，请求体分别通过Body、BodyBinary或者BodyUnicode参数来传递。请求体可以只使用其中一个参数，也可以使用一连串的分开的参数组成多请求体。例如：

web_custom_request（

……

"BodyUnicode=REPRICE"

"BodyBinary=\\x08\\x00\\xCC\\x02\\x00\\x00"

"Body=.\r\n"

"-dxjjtbw/(.tp?eg:ch/6--\r\n",

LAST）；

在上面的代码中，使用了3个参数来划分请求体，一个是Unicode段，一个是二进制段，最后一个是常规的字符串。最终的请求体是这3个参数按照在函数中的顺序连接起来的值。

还有一个很少用到的参数，Binary。它也能描述二进制请求体，但只允许函数中只有一个请求体参数。

所有的请求体都是ASCII字符，以null结束。

Body：表示规则的，可打印的字符串。无法表示空字节。所有的字符都以一个反斜杠表示。注意：在旧的脚本中,可以看见不可打印的字符在请求体中以16进制方式进行编码。（例如 “\\x5c”），在这种情况下，必须使用“Binary=1”来标识。空字节使用"file://0.0.0.0/"来表示。 相反，新脚本则会把把请求体分开放在不同的参数中（"Body=...", "BodyBinary=...", Body=..."）。

BodyBinary ：表示二进制代码。不可打印的字符在请求体中以16进制方式file://xhh/进行编码。在这里HH 表示十六进制值。空字节使用"file://0.0.0.0/"来表示。

BodyUnicode：美国英语， 特指拉丁 UTF-16LE（little-endian）编码。这种编码方式会在在每个字符末尾附加一个0字节，以便使字符更可读。但是在VuGen中实际的参数把所有的0字节都去掉的。但是在发送给Web 服务器之前, web_custom_request函数会重新添加0字节的。对于不可打印的字符,使用单反斜杠表示，无法表示空字节。

注意:如果请求体大于100K,会使用一个变量来代替Body参数。变量是在 lrw_custom_body.h中定义的。

Raw Body（目前仅适用与web_custom_request函数）：请求体是作为指针传递的，此指针指向一串数据。 二进制的请求体可以使用BodyBinary 属性来发送(或者使用Body 属性来传递,前提是必须设置"Binary=1" )。无论如何,这种方法需要使用转义字符反斜杠把不可打印的字符转换为ASCII字符。为了能有一种更简便的表现原始数据的方式，Raw Body属性应运而生，可以传递指向二进制数据的指针。

使用4个连续的参数集来表示指针，而且必须按照顺序排列：

    RAW_BODY_START

指向数据缓冲区的指针

（int） 长度

RAW_BODY_END

例子：

char *abc= .../* a pointer to the raw data */

web_custom_request("StepName",

               "URL=http://some.url ",

 "Method=POST",

     RAW_BODY_START,

     "abc",

                    3,

  RAW_BODY_END,

     LAST);

在应用中，即使设置了数据的长度为0，指针也必须有值，不能为空。

在“Binary=1”时，不能使用上面的语法传递原始数据。

数据缓冲区中的数据不能使用参数化。也就时说，缓冲区中的任何参数(例如 "{MyParam}")不能被正确的替代为相应的值，只会以字面值发送。

List of  Resource  Attributes
Web页面中的非HTML机制产生了资源列表，包含了Javascript, ActiveX, Java applets and Flash所请求的资源。VuGen's 的Recording 选项中，可以设置把这些资源录制在当前的操作中（默认是此设置）还是作为单独的步骤来录制。

支持以下资源：

URL