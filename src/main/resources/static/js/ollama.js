const input = document.getElementById("promptInput");
const chatContainer = document.getElementById("chatContainer");

input.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        sendPrompt();
    }
});

function getCurrentTime() {
    const now = new Date();
    const options = {
        hour: 'numeric',
        minute: '2-digit',
        hour12: true,
        timeZone: 'Asia/Kolkata'
    };
    const time = now.toLocaleTimeString('en-IN', options);
    return time.replace(/am|pm/, match => match.toUpperCase());
}

function appendMessage(sender, text, isLoading = false) {
    const messageDiv = document.createElement("div");
    messageDiv.classList.add("message", sender);

    const msgText = document.createElement("div");
    msgText.innerText = (sender === "user" ? "👤: " : "🤖: ") + text;

    const timestamp = document.createElement("div");
    timestamp.classList.add("timestamp");
    timestamp.innerText = getCurrentTime();

    messageDiv.appendChild(msgText);
    messageDiv.appendChild(timestamp);
    chatContainer.appendChild(messageDiv);

    if (!isLoading) messageDiv.scrollIntoView({ behavior: "smooth" });

    return messageDiv;
}

async function sendPrompt() {
    const prompt = input.value.trim();
    if (!prompt) return;

    appendMessage("user", prompt);
    input.value = "";

    const typingMsg = appendMessage("bot", "Typing...", true);

    try {
        const res = await fetch("http://localhost:8080/api/ollamai/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                prompt: prompt,
                model: "llama3.2"
            })
        });

        const data = await res.text(); // Expect plain text

        setTimeout(() => {
            chatContainer.removeChild(typingMsg);
            appendMessage("bot", data || "No response");
        }, 800);
    } catch (error) {
        chatContainer.removeChild(typingMsg);
        appendMessage("bot", `Error: ${error.message}`);
    }
}

function clearChat() {
    chatContainer.innerHTML = "";
}

function toggleDarkMode() {
    document.body.classList.toggle("dark-mode");
}

// ✅ 🎙️ Voice Input
const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
const recognition = SpeechRecognition ? new SpeechRecognition() : null;

if (recognition) {
    recognition.lang = 'en-IN';
    recognition.continuous = false;
    recognition.interimResults = false;

    recognition.onresult = (event) => {
        const transcript = event.results[0][0].transcript;
        input.value = transcript;
        sendPrompt();
    };

    recognition.onerror = (event) => {
        alert("Voice input error: " + event.error);
    };

    // 🎙️ Add mic button to input area
    const micButton = document.createElement("button");
    micButton.innerText = "🎙️";
    micButton.style.marginLeft = "5px";
    micButton.style.borderRadius = "10px";
    micButton.style.cursor = "pointer";
    micButton.title = "Speak your message";
    micButton.onclick = () => recognition.start();

    const inputArea = document.getElementById("inputArea");
    inputArea.appendChild(micButton);
} else {
    console.warn("SpeechRecognition API not supported in this browser.");
}


