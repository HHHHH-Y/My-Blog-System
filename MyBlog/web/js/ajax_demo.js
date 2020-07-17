// 选择器 (Selector) button 的意思, 选择所有的 button 标签
let buttonElement = document.querySelector("button");

function onClick(ev) {
    // 有用户点击 button, 发起 ajax 请求
    let xhr = new XMLHttpRequest();
    xhr.open("get", "/api/article_list.json");
    xhr.onreadystatechange = function(ev) {
        if(xhr.readyState == XMLHttpRequest.DONE) { // ajax 请求完成时
            if(xhr.status == 200) {                 // HTTP 响应 200 的时候
                let articleList = JSON.parse(xhr.responseText); // 把结果按照 JSON 格式解析
                console.log(articleList);

                // 把得到的结果放入 html 中
                let tbody = document.querySelector("tbody");  // html 输出的标签
                for (let i in articleList) {
                    let article = articleList[i];
                    let html = "<tr>" +
                        "<td>" + article["id"] + "</td>" +
                        "<td>" + article["title"] + "</td>" +
                        "<td>" + article["published_at"] + "</td>" +
                        "<td>" + article["author"]["id"] + "</td>" +
                        "<td>" + article["author"]["username"] + "</td>" +
                        "</tr>";
                    console.log(html);
                    tbody.innerHTML += html;  // 把拼接好的 html, 放入标签中
                }
            }
        }
    };
    xhr.send();
}

// 如果在 button 这个标签上发生了 click(点击) 事件, 请执行 onClick 函数
// 每次用户点击 button, 就会运行 onClick 方法
buttonElement.addEventListener("click", onClick);