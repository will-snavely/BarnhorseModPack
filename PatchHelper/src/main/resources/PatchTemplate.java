@SpirePatch(
        clz = {{ targetClass }},
        method = "{{ targetMethod }}"
)
class {{patchClassName}} {
@SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(Object thisRef) {
        PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
    }
}