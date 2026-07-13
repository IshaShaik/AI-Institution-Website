
// async function loadConceptAnalytics() {
//     const attemptId = localStorage.getItem("attemptId");
//     if (!attemptId) {
//         alert("Attempt ID missing");
//         return;
//     }

//     try {
//         // 1. Dono APIs se parallel data mangwana
//         const [topicRes, resultRes] = await Promise.all([
//             fetch(`http://localhost:8080/api/student/exam/topic-progress/${attemptId}`),
//             fetch(`http://localhost:8080/api/student/exam/result-details/${attemptId}`)
//         ]);

//         if (topicRes.status === 403 || resultRes.status === 403) {
//             alert("Result not unlocked yet");
//             return;
//         }

//         const topicData = await topicRes.json();
//         const resultData = await resultRes.json();
//         const container = document.getElementById("concept-analytics-area");

//         // 2. Total Marks Calculation (Admin vs Student)
//         const studentMarks = resultData.totalMarks || 0;
//         const adminMarks = resultData.totalPossibleMarks || 0;
//         const overallPercent = adminMarks > 0 ? Math.round((studentMarks / adminMarks) * 100) : 0;

//         // 3. Main Layout Render
//         container.innerHTML = `
//         <style>
//             .analytics-flex-container {
//                 position:absolute; left:0px; top:50px; display: flex;
//                 gap: 40px; width: 950px; align-items: stretch;
//             }

//             /* Purana Chart Box Style */
//             .chart-wrapper {
//                 flex: 1.5; border-radius: 24px; background: #0d1117;
//                 border: 1px solid #21262d; padding: 30px; font-family: 'Inter', sans-serif;
//             }

//             /* 🌸 Pink Heartbeat Box */
//             .pink-info-box {
//                 flex: 1; border-radius: 24px; background: #0d1117;
//                 border: 2px solid pink; padding: 30px; color: white;
//                 text-align: center; font-family: 'Inter', sans-serif;
//                 box-shadow: 0 0 20px rgba(255, 192, 203, 0.1);
//             }

//             /* --- Heartbeat Animation --- */
//             .heartbeat-wrapper {
//                 width: 80px; height: 180px; background: #161b22; border-radius: 50px;
//                 margin: 20px auto; position: relative; overflow: hidden; border: 2px solid #30363d;
//             }

//             .heartbeat-fill {
//                 position: absolute; bottom: 0; width: 100%;
//                 background: linear-gradient(0deg, #ff2e63, #ff6b6b);
//                 transition: height 1.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
//                 animation: pulse-glow 1.2s infinite ease-in-out;
//             }

//             @keyframes pulse-glow {
//                 0%, 100% { filter: brightness(1) drop-shadow(0 0 5px #ff2e63); transform: scaleX(1); }
//                 50% { filter: brightness(1.3) drop-shadow(0 0 15px #ff6b6b); transform: scaleX(1.03); }
//             }

//             .score-text { font-size: 42px; font-weight: 800; color: pink; margin: 10px 0; }
//             .score-label { color: #8b949e; font-size: 14px; }

//             /* --- Purani Bar Styling --- */
//             .chart-container { display: flex; height: 350px; position: relative; padding-left: 50px; padding-bottom: 50px; }
//             .y-axis { position: absolute; left: 0; top: 0; bottom: 50px; display: flex; flex-direction: column-reverse; justify-content: space-between; font-size: 13px; color: #8b949e; width: 40px; text-align: right; padding-right: 10px; }
//             .chart-area { display: flex; align-items: flex-end; justify-content: flex-start; gap: 40px; flex: 1; position: relative; border-left: 1px solid #30363d; border-bottom: 1px solid #30363d; }
//             .grid-lines { position: absolute; width: 100%; height: 100%; display: flex; flex-direction: column-reverse; justify-content: space-between; pointer-events: none; }
//             .grid-line { width: 100%; border-top: 1px solid rgba(255,255,255,0.05); }
//             .bar-box { position: relative; left: 20px; display: flex; flex-direction: column; align-items: center; height: 100%; justify-content: flex-end; min-width: 55px; }
            
//             .bar {
//                 width: 50px; height: 0; border-radius: 14px 14px 4px 4px;
//                 transition: height 1.5s ease; position: relative; cursor: pointer; z-index: 5;
//             }
//             .bar:hover { filter: brightness(1.2); }

//             /* --- Purana Tooltip --- */
//             .tooltip {
//                 position: absolute; top: -80px; left: 50%; transform: translateX(-50%);
//                 background: #161b22; color: #fff; padding: 10px 15px; border-radius: 10px;
//                 font-size: 13px; opacity: 0; visibility: hidden; transition: 0.3s;
//                 white-space: nowrap; border: 1px solid #30363d; z-index: 100;
//             }
//             .bar:hover .tooltip { opacity: 1; visibility: visible; }

//             .topic-name {
//                 position: absolute; bottom: -55px; font-size: 11px; color: #8b949e;
//                 text-align: center; width: 60px; white-space: normal; word-wrap: break-word; line-height: 1.2;
//             }
//         </style>

//         <div class="analytics-flex-container">
//             <div class="chart-wrapper">
//                 <div class="chart-container">
//                     <div class="y-axis">
//                         <span></span><span>10</span><span>20</span><span>30</span><span>40</span><span>50</span><span>60</span><span>70</span><span>80</span><span>90</span><span>100</span>
//                     </div>
//                     <div class="chart-area" id="barsArea">
//                         <div class="grid-lines">
//                             ${Array(11).fill('<div class="grid-line"></div>').join('')}
//                         </div>
//                     </div>
//                 </div>
//             </div>

//             <div class="pink-info-box">
//                 <h3 style="color: pink; margin-top: 0; font-size: 20px;">Overall Performance</h3>
                
//                 <div class="heartbeat-wrapper">
//                     <div class="heartbeat-fill" id="overallFill" style="height: 0%;"></div>
//                 </div>

//                 <div class="score-text">${overallPercent}%</div>
//                 <div class="score-label">
//                     Obtained: <span style="color:white; font-weight:bold;">${studentMarks}</span> / 
//                     Admin Total: <span style="color:pink; font-weight:bold;">${adminMarks}</span>
//                 </div>

//                 <div style="margin-top: 25px; padding: 15px; border: 1px dashed pink; border-radius: 12px; background: rgba(255, 192, 203, 0.05);">
//                     <small style="color: pink;">Status: ${overallPercent >= 50 ? 'Exam Passed 🏆' : 'Needs Improvement ⚠'}</small>
//                 </div>
//             </div>
//         </div>
//         `;

//         // 4. Render Bars Loop (Wahi purana gradient aur logic)
//         const barsArea = document.getElementById("barsArea");
//         const colors = [
//             "linear-gradient(180deg,#ff512f,#dd2476)",
//             "linear-gradient(180deg,#00c6ff,#0072ff)",
//             "linear-gradient(180deg,#00ff87,#60efff)",
//             "linear-gradient(180deg,#f7971e,#ffd200)",
//             "linear-gradient(180deg,#8e2de2,#4a00e0)"
//         ];

//         topicData.forEach((topic, index) => {
//             let percent = topic.totalMarks > 0 ? Math.round((topic.obtainedMarks / topic.totalMarks) * 100) : 0;
//             let strengthMessage = percent <= 40 ? "⚠ Weak" : percent <= 70 ? "🙂 Average" : percent <= 85 ? "💪 Strong" : "🏆 Excellent";
//             const color = colors[index % colors.length];

//             const barHTML = `
//                 <div class="bar-box">
//                     <div class="bar" id="bar${index}" style="background:${color};">
//                         <div class="tooltip">
//                             <strong>${topic.topicName}</strong><br>
//                             Mastery: ${percent}% <br>
//                             <span style="font-size:12px;opacity:0.8;">${strengthMessage}</span>
//                         </div>
//                     </div>
//                     <div class="topic-name">${topic.topicName}</div>
//                 </div>
//             `;
//             barsArea.insertAdjacentHTML("beforeend", barHTML);

//             // Bar height animation
//             setTimeout(() => {
//                 const bar = document.getElementById(`bar${index}`);
//                 if (bar) bar.style.height = `${percent}%`;
//             }, 200 + (index * 150));
//         });

//         // 5. Heartbeat Fill Animation
//         setTimeout(() => {
//             const fill = document.getElementById("overallFill");
//             if(fill) fill.style.height = `${overallPercent}%`;
//         }, 600);

//     } catch (error) {
//         console.error("Error loading analytics:", error);
//     }
// }
/* ============================================= */
/* UPDATED: DYNAMIC CONCEPT ANALYTICS LOGIC      */
/* ============================================= */

// Is function mein humne (selectedId) parameter add kiya hai
async function loadConceptAnalytics(selectedId = null) {
    
    // Agar dropdown se koi ID aayi hai toh wo use karo, warna purani default logic
    const attemptId = selectedId || localStorage.getItem("attemptId");

    if (!attemptId) {
        // alert("Attempt ID missing");
        return;
    }

    try {
        // 1. Dono APIs se parallel data mangwana (Dynamic ID ke saath)
        const [topicRes, resultRes] = await Promise.all([
            fetch(`http://localhost:8080/api/student/exam/topic-progress/${attemptId}`),
            fetch(`http://localhost:8080/api/student/exam/result-details/${attemptId}`)
        ]);

        if (topicRes.status === 403 || resultRes.status === 403) {
            // alert("Result not unlocked yet");
            return;
        }

        const topicData = await topicRes.json();
        const resultData = await resultRes.json();
        const container = document.getElementById("concept-analytics-area");

        if(!container) return; // Guard clause

        // 2. Total Marks Calculation
        const studentMarks = resultData.totalMarks || 0;
        const adminMarks = resultData.totalPossibleMarks || 0;
        const overallPercent = adminMarks > 0 ? Math.round((studentMarks / adminMarks) * 100) : 0;

        // 3. Main Layout Render (Aapka purana CSS aur HTML)
        container.innerHTML = `
        <style>
            .analytics-flex-container {
                position:absolute; left:0px; top:50px; display: flex;
                gap: 40px; width: 950px; align-items: stretch;
            }
            .chart-wrapper {
                flex: 1.5; border-radius: 24px; background: #0d1117;
                border: 1px solid #21262d; padding: 30px; font-family: 'Inter', sans-serif;
            }
            .pink-info-box {
                flex: 1; border-radius: 24px; background: #0d1117;
                border: 2px solid pink; padding: 30px; color: white;
                text-align: center; font-family: 'Inter', sans-serif;
                box-shadow: 0 0 20px rgba(255, 192, 203, 0.1);
            }
            .heartbeat-wrapper {
                width: 80px; height: 180px; background: #161b22; border-radius: 50px;
                margin: 20px auto; position: relative; overflow: hidden; border: 2px solid #30363d;
            }
            .heartbeat-fill {
                position: absolute; bottom: 0; width: 100%;
                background: linear-gradient(0deg, #ff2e63, #ff6b6b);
                transition: height 1.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
                animation: pulse-glow 1.2s infinite ease-in-out;
            }
            @keyframes pulse-glow {
                0%, 100% { filter: brightness(1) drop-shadow(0 0 5px #ff2e63); transform: scaleX(1); }
                50% { filter: brightness(1.3) drop-shadow(0 0 15px #ff6b6b); transform: scaleX(1.03); }
            }
            .score-text { font-size: 42px; font-weight: 800; color: pink; margin: 10px 0; }
            .score-label { color: #8b949e; font-size: 14px; }
            .chart-container { display: flex; height: 350px; position: relative; padding-left: 50px; padding-bottom: 50px; }
            .y-axis { position: absolute; left: 0; top: 0; bottom: 50px; display: flex; flex-direction: column-reverse; justify-content: space-between; font-size: 13px; color: #8b949e; width: 40px; text-align: right; padding-right: 10px; }
            .chart-area { display: flex; align-items: flex-end; justify-content: flex-start; gap: 40px; flex: 1; position: relative; border-left: 1px solid #30363d; border-bottom: 1px solid #30363d; }
            .grid-lines { position: absolute; width: 100%; height: 100%; display: flex; flex-direction: column-reverse; justify-content: space-between; pointer-events: none; }
            .grid-line { width: 100%; border-top: 1px solid rgba(255,255,255,0.05); }
            .bar-box { position: relative; left: 20px; display: flex; flex-direction: column; align-items: center; height: 100%; justify-content: flex-end; min-width: 55px; }
            .bar {
                width: 50px; height: 0; border-radius: 14px 14px 4px 4px;
                transition: height 1.5s ease; position: relative; cursor: pointer; z-index: 5;
            }
            .tooltip {
                position: absolute; top: -80px; left: 50%; transform: translateX(-50%);
                background: #161b22; color: #fff; padding: 10px 15px; border-radius: 10px;
                font-size: 13px; opacity: 0; visibility: hidden; transition: 0.3s;
                white-space: nowrap; border: 1px solid #30363d; z-index: 100;
            }
            .bar:hover .tooltip { opacity: 1; visibility: visible; }
            .topic-name {
                position: absolute; bottom: -55px; font-size: 11px; color: #8b949e;
                text-align: center; width: 60px; white-space: normal; word-wrap: break-word; line-height: 1.2;
            }
        </style>

        <div class="analytics-flex-container">
            <div class="chart-wrapper">
                <div class="chart-container">
                    <div class="y-axis">
                        <span></span><span>10</span><span>20</span><span>30</span><span>40</span><span>50</span><span>60</span><span>70</span><span>80</span><span>90</span><span>100</span>
                    </div>
                    <div class="chart-area" id="barsArea">
                        <div class="grid-lines">
                            ${Array(11).fill('<div class="grid-line"></div>').join('')}
                        </div>
                    </div>
                </div>
            </div>

            <div class="pink-info-box">
                <h3 style="color: pink; margin-top: 0; font-size: 20px;">Overall Performance</h3>
                <div class="heartbeat-wrapper">
                    <div class="heartbeat-fill" id="overallFill" style="height: 0%;"></div>
                </div>
                <div class="score-text">${overallPercent}%</div>
                <div class="score-label">
                    Obtained: <span style="color:white; font-weight:bold;">${studentMarks}</span> / 
                    Admin Total: <span style="color:pink; font-weight:bold;">${adminMarks}</span>
                </div>
                <div style="margin-top: 25px; padding: 15px; border: 1px dashed pink; border-radius: 12px; background: rgba(255, 192, 203, 0.05);">
                    <small style="color: pink;">Status: ${overallPercent >= 50 ? 'Exam Passed 🏆' : 'Needs Improvement ⚠'}</small>
                </div>
            </div>
        </div>
        `;

        // 4. Render Bars Loop
        const barsArea = document.getElementById("barsArea");
        const colors = ["linear-gradient(180deg,#ff512f,#dd2476)", "linear-gradient(180deg,#00c6ff,#0072ff)", "linear-gradient(180deg,#00ff87,#60efff)", "linear-gradient(180deg,#f7971e,#ffd200)", "linear-gradient(180deg,#8e2de2,#4a00e0)"];

        topicData.forEach((topic, index) => {
            let percent = topic.totalMarks > 0 ? Math.round((topic.obtainedMarks / topic.totalMarks) * 100) : 0;
            let strengthMessage = percent <= 40 ? "⚠ Weak" : percent <= 70 ? "🙂 Average" : percent <= 85 ? "💪 Strong" : "🏆 Excellent";
            const color = colors[index % colors.length];

            const barHTML = `
                <div class="bar-box">
                    <div class="bar" id="bar${index}" style="background:${color};">
                        <div class="tooltip">
                            <strong>${topic.topicName}</strong><br>
                            Mastery: ${percent}% <br>
                            <span style="font-size:12px;opacity:0.8;">${strengthMessage}</span>
                        </div>
                    </div>
                    <div class="topic-name">${topic.topicName}</div>
                </div>
            `;
            barsArea.insertAdjacentHTML("beforeend", barHTML);

            setTimeout(() => {
                const bar = document.getElementById(`bar${index}`);
                if (bar) bar.style.height = `${percent}%`;
            }, 200 + (index * 150));
        });

        // 5. Heartbeat Animation
        setTimeout(() => {
            const fill = document.getElementById("overallFill");
            if(fill) fill.style.height = `${overallPercent}%`;
        }, 600);

    } catch (error) {
        console.error("Error loading analytics:", error);
    }
}