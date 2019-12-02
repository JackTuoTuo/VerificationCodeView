package com.yey.vcodevy;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * 参考博客：
 * https://blog.csdn.net/jdsjlzx/article/details/25064867
 * 符号大全
 * http://lovefree365.pixnet.net/blog/post/399059086-2017
 */
public class CustomPasswordTransformationMethod extends PasswordTransformationMethod {
    public int etPwdMode;

    public CustomPasswordTransformationMethod(int mEtPwdMode) {
        etPwdMode = mEtPwdMode;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        /**
         * ✓ ✔ ✗ ✘ ∨ √ ˇ ☐ ☑ ☒
         * ▲ ▼ ▴ ▾ ▶ ▸ ► ◀ ◂ ◄ ◢ ◣
         * △ ▽ ▵ ▿ ▷ ▹ ▻ ◁ ◃ ◅ ◥ ◤
         * ◅ ◢ ◣ ◤ ◥ ◬ ◭ ◮ ∇ ∆ ⊿ ⟁
         * ⧍ ⧊ ⧋ ⧌ ⧐ ⧏ ⧔ ⧕ ⧑ ⧒ ⧓ ⧖ ⧗
         * ⍒ ⌺ ⍙ ⏅ ⏃ ⏄ ⍋ ⍫ ⍱ ⍲ ⍢
         * ◦○ ◯ ⦿ ◉ ● • ⊖ ⊙ ⊚ ⊕ ⊗ ⊘ ⊛ ⊜ ⊝ ◌ ◍ ◎ ◐ ◑ ◒ ◓ ◔ ◕ ◖ ◗  ❍ ⌒  ◜ ◝ ◞ ◟ ◠ ◡ ⍥ ⧀ ⧁ ⦰ ⦷ ⦹ ⦺ ⦻ ⦼ ⦽ ⧂ ⧃ ⧭ ⍜ ♾ ⏀ ⏁ ⏂ ⍉ ⌽ ⎊ ⧬ ⧭ ◵ ⦻ ⧲ ⧳ ⚇ ⚆ ⚉ ⚈
         * ♥ ❤ ❥ ♡ დ ღ ❣ ❦ ❧ ❤  ღ  ❥  ♡ 💓  💔  💕  💖  💗  💘  💙  💚  💛  💜  💝  💞  💟
         * ↑ ↓ → ← ↔ ↕ ↗ ↖ ↘ ↙ ↛ ↚  ⤴ ⤵ ↵ ↴ ↱ ↰ ↳ ↲
         * ↟ ↡ ↠ ↞ ↣ ↢ ↥ ↧ ↦ ↤ ↨ ↬ ↫ ↝ ↜ ↭ ↮  ↯ ↸
         * ↾ ↿↼ ⇀⇂ ⇃↽ ⇁  ⥒ ⥓ ⥔ ⥕ ⥖ ⥗ ⥘ ⥙ ⤞ ⤝ ⤠ ⤟
         * ⇒ ⇐ ⇏ ⇍ ⇎ ⇕ ⇗ ⇖ ⇘ ⇙ ➾ ⇛ ⇚ ⇝ ⇜ ⇞ ⇟ ⇢ ⇠ ⇡ ⇣ ⇥ ⇤
         * ⥂ ⥃ ⥄ ⥅ ⥆ ↹ ⇈ ⇊ ⇇ ⇉ ⇅ ⇄ ⇆ ⇋ ⇌
         * ↩ ↪ ⤣ ⤤ ⤥ ⤦ ⤡ ⤢ ⤧ ⤨ ⤩ ⤪ ⤭ ⤮ ⤱ ⤲ ⤯ ⤰
         * ⤻ ⤺ ⤼ ⤽ ⤹ ⤸ ⤿ ⤾ ⥀ ⥁ ↺ ↻
         * ➔ ➘ ➙ ➚ ➛ ➜ ➝ ➞ ➟ ➠ ➡ ➢ ➣ ➤ ➥ ➦ ➧ ➨ ➲
         * ➩ ➪ ➫ ➬ ➭ ➮ ➯ ➱
         * ⇦ ⇧ ⇨ ⇩ ⇪ ⌅ ⌆ ⌤ ⏎
         * ☇ ☈ ☊ ☋ ☌ ☍
         * ➳ ➴ ➵ ➶ ➷ ➸ ➹ ➺ ➻ ➼ ➽
         * ㄅ ㄆ ㄇ ㄈ ㄉ ㄊ ㄋ ㄌ ㄍ ㄎ
         * ㄏ ㄐ ㄑ ㄒ ㄓ ㄔ ㄕ ㄖ ㄗ ㄘ
         * ㄙ ㄚ ㄛ ㄜ ㄝ ㄞ ㄟ ㄠ ㄡ ㄢ
         * ㄣ ㄤ ㄥ ㄦ ㄧ ㄨ ㄩ ˙ ¯ ˊ ˇ ˋ ˙
         * ， 、 。 ！ ： ； ︰ ˙ ‥ ‧  ‵ ❛ ❜ ❝ ❞ 〃 〝 〞 ﹁ ﹂ ﹃ ﹄ ﹐ ﹒ ﹔ ﹔ ﹕ ＃ ＄ ％ ＆ ＊ ． ？ ＠ ～ • … ¿ ⁇ ⁉ ⁈  ‽ ⸘  ❢ “ ‘ · ′ ” ’
         * （ ） ＜ ＞ ｛ ｝ 〈 〉 《 》 「 」 『 』 【 】 〔 〕 ﹙ ﹚ ﹛ ﹜ ﹝ ﹞ ﹤ ﹥
         * ︵ ︷ ︹ ︻ ︽  ︿ ﹁ ﹃
         * ︶ ︸ ︺ ︼ ︾  ﹀ ﹂ ﹄
         * ★ ☆ ✪ ✦ ✧ ✩ ✫ ✬ ✭ ✮ ✯ ✰ ✡ ⁂ ⁎ ⁑ * ⁕ ✢ ✣ ✥ ✱ ✲ ✳ ✴ ✵ ✶ ✷ ✸ ✹ ✺
         * ✿ ❀ ❁ ❂ ❃ ❇ ❈ ❉ ❊ ❋ ✻ ✼ ✤ ✽ ✾
         * ０ １ ２ ３ ４ ５ ６ ７ ８ ９
         * ⒈ ⒉ ⒊ ⒋ ⒌ ⒍ ⒎ ⒏ ⒐ ⒑ ⒒ ⒓ ⒔ ⒕ ⒖ ⒗ ⒘ ⒙ ⒚ ⒛
         * 🄁 🄂 🄃 🄄 🄅 🄆 🄇 🄈 🄉 🄊
         * ⑴ ⑵ ⑶ ⑷ ⑸ ⑹ ⑺ ⑻ ⑼ ⑽
         * ⑴ ⑵ ⑶ ⑷ ⑸ ⑹ ⑺ ⑻ ⑼ ⑽ ⑾ ⑿ ⒀ ⒁ ⒂ ⒃ ⒄ ⒅ ⒆ ⒇
         * ⓿ ❶ ❷ ❸ ❹ ❺ ❻ ❼ ❽ ❾ ❿
         * ⓪ ① ② ③ ④ ⑤ ⑥ ⑦ ⑧ ⑨ ⑩ ⑪ ⑫ ⑬ ⑭ ⑮ ⑯ ⑰ ⑱ ⑲ ⑳
         * ㉑ ㉒ ㉓ ㉔ ㉕ ㉖ ㉗ ㉘ ㉙ ㉚ ㉛ ㉜ ㉝ ㉞ ㉟ ㊱ ㊲ ㊳ ㊴ ㊵ ㊶ ㊷ ㊸ ㊹ ㊺ ㊻ ㊼ ㊽ ㊾ ㊿
         * ⓵ ⓶ ⓷ ⓸ ⓹ ⓺ ⓻ ⓼ ⓽ ⓾
         * ㈠ ㈡ ㈢ ㈣ ㈤ ㈥ ㈦ ㈧ ㈨ ㈩
         * ㊀ ㊁ ㊂ ㊃ ㊄ ㊅ ㊆ ㊇ ㊈ ㊉
         * Ⅰ Ⅱ Ⅲ Ⅳ Ⅴ Ⅵ Ⅶ Ⅷ Ⅸ Ⅹ Ⅺ Ⅻ
         * ⅰ ⅱ ⅲ ⅳ ⅴ ⅵ ⅶ ⅷ ⅸ ⅹ ⅺ ⅻ
         * ∎ ⊞ ⊟ ⊠ ⊡ ⎔ ⎚ ▀
         * ▁ ▂ ▃ ▄ ▅ ▆ ▇ █ ▉ ▊ ▋ ▋ ▌ ▍ ▎ ▏ ▐ ░ ▒ ▓ ▔ ■ □ ▢ ▣ ▤ ▥ ▦ ▧ ▨ ▩ ▪ ▫ ▬ ▭ ▮ ▯ ▰ ▱ ◘ ◧ ◨ ◩ ◪ ◫ ☖ ☗ ❏ ❐ ❑ ❒ ❖ ❘ ❙ ❚ ◊  ▩ ▢ ⊡ ⧉ ⟥ ⟤ ⧆ ⧈ ⧇ ⧄ ⧅ ⧯ ⧮ ⌷ ⌸ ⌹ ⍰ ⍞ ⍠ ⍁ ⍂ ⎅ ⏍ ⏛
         * <p>
         * ◆ ◇ ◈ ⟐ ⋄ ⟐ ⟡ ⎏ ⎐ ⎑ ⎒ ⟢ ⟣ ⧪ ⧫ ⧰ ⍚ ⟕ ⟖ ⟗ ⟠
         * ＋  －  ×  ÷  ／  ＝  ≠  √  ±
         * ⊥   ‖   ∠   ⌒   ⊙   ≡   ≌    △
         * ∝   ∧   ∨   ～   ∫   ≠  ≤  ≥  ≈  ∞   ∶
         * ∪   ∩   ∈
         * ∑   π（圓周率）
         * ∴  ∵  ∶  ∷  ∸  ∹  ∺  ∻  ∼  ∽  ∾  ∿  ≀
         * |a|    ⊥    ∽    △    ∠    ∩    ∪    ≠    ≡    ±    ≥    ≤    ∈    ←
         * ↑    →    ↓    ↖    ↗    ↘    ↙    ‖    ∧    ∨
         * ⋙ ⋘  ⋚ ⋛ ⋟ ⋞ ⋣ ⋢ ⋥ ⋤ ⋩ ⋨ ⋧ ⋦ ⋫ ⋪ ⋭ ⋬ ⋰ ⋱  ⋮ ⋯
         * ½ ⅓ ⅔ ¼ ¾ ⅕ ⅖ ⅗ ⅘ ⅙ ⅚ ⅐ ⅛ ⅜ ⅝ ⅞ ⅑ ⅒ ⅟ ↉
         * % ‰ ‱
         * ƒ √ ∛ ∜ ∝ ∞ ∟ ∠ ∡ ∢ ∀ ∁ ∂ ∃ ∄ ∅ ∆ ∇
         * ☺ ☻ ☹  〠  웃 유
         * 😀 😁 😂 😃 😄 😅 😆 😇 😈
         * 😉 😊 😋 😌 😍 😎 😏 😐 😑
         * 😒 😓 😔 😕 😖 😗 😘 😙 😚
         * 😛 😜 😝 😞 😟 😠 😡 😢 😣
         * 😤 😥 😦 😧 😨 😩 😪 😫 😬
         * 😭 😮 😯 😰 😱 😲 😳 😴 😵
         * 😶 😷 😸 😹 😺 😻 😼 😽 😾
         * 😿 🙀 🙈 🙉 🙊 🙆 🙋 🙍 🙎
         * 🙅
         * ☿　♀　♁　♂　⚢　⚣　⚤　⚥　⚦　⚧　⚨　⚩
         * ♈ ♉ ♊ ♋ ♌ ♍ ♎ ♏ ♐ ♑ ♒ ♓
         * ☀ ☁ ☂ ϟ  ☄ ☉ ☼ ☾ ☽ ♁ ♨ ❄ ❅ ❆ ☃ ⭐ ☁ ⛅ 🌤 🌥 🌦 🌧 🌩 🌪 🌫 🌀 🌈 🌂 ☔ ⚡  ⛄
         * ☛ ☚ ☞ ☜ ☟
         * 👋 🤚 🖐🖖 ✋ 👌 ✌ 🤞 🤟 🤘 🤙 👉👈 👆 👇 ☝ 👍 👎 ✊ 👊🤜 🤛 👏 🙌 🤲 👐 🙏 ✍ 💪
         * 👋🏻 🤚🏻 🖐🏻🖖🏻 ✋🏻 👌🏻 ✌🏻 🤞🏻 🤟🏻 🤘🏻 🤙🏻 👉🏻👈🏻 👆🏻 👇🏻 ☝🏻 👍🏻 👎🏻 ✊🏻 👊🏻🤜🏻 🤛🏻 👏🏻 🙌🏻 🤲🏻 👐🏻 🙏🏻 ✍🏻 💪🏻
         * 👋🏽 🤚🏽 🖐🏽🖖🏽 ✋🏽 👌🏽 ✌🏽 🤞🏽 🤟🏽 🤘🏽 🤙🏽 👉🏽👈🏽 👆🏽 👇🏽 ☝🏽 👍🏽 👎🏽 ✊🏽 👊🏽🤜🏽 🤛🏽 👏🏽 🙌🏽 🤲🏽 👐🏽 🙏🏽 ✍🏽 💪🏽
         */
        public char charAt(int index) {
            //char mChar = mSource.charAt(index);
            char mChar = '•';
            switch (etPwdMode) {
                case 1:
                    mChar = '*';
                    break;
                case 2:
                    mChar = '●';
                    break;
                case 3:
                    mChar = '♥';
                    break;
                case 4:
                    mChar = '▉';
                    break;
                case 5:
                    mChar = '☻';
                    break;
                case 6:
                    mChar = '◆';
                    break;
                default:
                    mChar = '•';
                    break;
            }
            return mChar; // This is the important part
        }

        public int length() {
            return mSource.length(); // Return default
        }

        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }

    }
}


