package core;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import domain.Athlete;
import domain.Contest;
import domain.ContestDetailed;
import utils.Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class CoreModule {
    private static final String[] TYPE = {"Preliminary", "Semifinal", "Final"};
    // 输出所有选手信息
    public void displayAllPlayersInfo() {
        try {
            // 读取 athletes.json 文件
            StringBuilder jsonContent = new StringBuilder();
            InputStream in = getClass().getClassLoader().getResourceAsStream("athletes.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            br.close();

            // 解析 JSON 数据
            // 提取 各国家代表团 列表
            JSONArray jsonArray = JSON.parseArray(jsonContent.toString());
            // 遍历每个国家
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject nation = jsonArray.getJSONObject(i);
                // 获得 国家名字
                String countryName = nation.getString("CountryName");
                // 提取 参赛队员 列表
                JSONArray participations = nation.getJSONArray("Participations");

                for (int k = 0; k < participations.size(); k++) {
                    // 获得 参赛队员
                    JSONObject participation = participations.getJSONObject(k);

                    int gender = participation.getIntValue("Gender");
                    String preferredLastName = participation.getString("PreferredLastName");
                    String preferredFirstName = participation.getString("PreferredFirstName");

                    Athlete athlete = new Athlete(countryName, gender, preferredLastName, preferredFirstName);
                    // 用 Athlete 类 重写的 toString 方法直接输出
                    Utility.write(athlete.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 输出每个比赛项目决赛的结果
    public void displayResults(String contestName) {
        List<Contest> listFinal = displayResults(contestName, 2);
        for (Contest contest : listFinal) {
            try {
                Utility.write(contest.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 查询每个比赛项目的结果
    private List<Contest> displayResults(String contestName, int contestType) {
        List<Contest> contests = new ArrayList<>();
        try {
            // 读取 JSON 文件
            StringBuilder jsonContent = new StringBuilder();
            String path = contestName + ".json";
            InputStream in = getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            br.close();

            JSONObject jsonObject = JSON.parseObject(jsonContent.toString());

            // 提取 Heats 列表
            JSONArray heats = jsonObject.getJSONArray("Heats");

            for (int i = 0; i < heats.size(); i++) {
                JSONObject heat = heats.getJSONObject(i);
                // 获取 当前比赛 级别
                String name = heat.getString("Name");
                // 如果 不是 目标类型, 退出
                if (!name.equals(TYPE[contestType])) {
                    continue;
                }
                // 获取 当前比赛 结果
                JSONArray results = heat.getJSONArray("Results");
                for (int k = 0; k < results.size(); k++) {
                    // 获取 每位运动员的 成绩
                    JSONObject result = results.getJSONObject(k);
                    // 总成绩
                    String totalPoints = result.getString("TotalPoints");
                    // Rank
                    int rank = result.getIntValue("Rank");
                    // FullName (注意：如果是双人跳水，FullName为 "A / B" 格式，需要 replace)
                    String fullName = result.getString("FullName").replace('/', '&');
                    // 创建 Contest 对象
                    Contest contest = new Contest(totalPoints, rank, fullName, new ArrayList<>());
                    // 获取 每一次跳水 的 score 添加到 contest 中的 dives 集合
                    JSONArray dives = result.getJSONArray("Dives");
                    for (int j = 0; j < dives.size(); j++) {
                        contest.getDives().add(dives.getJSONObject(j).getString("DivePoints"));
                    }
                    // 加入 contests 集合中
                    contests.add(contest);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contests;
    }

    // 输出每个比赛项目结果（详细）
    public void displayDetailedResults(String contestName) {
        HashMap<String, ContestDetailed> map = new HashMap<>();
        for (Contest preliminaryContest : displayResults(contestName, 0)) {
            map.computeIfAbsent(preliminaryContest.getFullName(), k -> new ContestDetailed()).contests[0] = preliminaryContest;
        }
        for (Contest semiFinalContest : displayResults(contestName, 1)) {
            map.computeIfAbsent(semiFinalContest.getFullName(), k -> new ContestDetailed()).contests[1] = semiFinalContest;
        }
        for (Contest finalContest : displayResults(contestName, 2)) {
            map.computeIfAbsent(finalContest.getFullName(), k -> new ContestDetailed()).contests[2] = finalContest;
        }

        List<ContestDetailed> lst = new ArrayList<>(map.values());
        lst.sort(new Comparator<ContestDetailed>() {
            @Override
            public int compare(ContestDetailed a, ContestDetailed b) {
                if (a.contests[0] != null) {
                    return a.contests[0].getRank() - b.contests[0].getRank();
                }
                else if (a.contests[1] != null) {
                    return a.contests[1].getRank() - b.contests[1].getRank();
                }
                else {
                    return a.contests[2].getRank() - b.contests[2].getRank();
                }
            }
        });

        for (ContestDetailed contestDetailed : lst) {
            try {
                Utility.write(contestDetailed.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
