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
        msgText.innerText = (sender === "user" ? "👤 " : "🤖 ") + text;

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
            const res = await fetch("/api/chat", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ prompt })
            });

            const data = await res.json();

            // Simulate typing delay
            setTimeout(() => {
                chatContainer.removeChild(typingMsg);
                appendMessage("bot", data.reply || "No response");
            }, 800); // 800ms delay
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



