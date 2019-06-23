package ehacks.bsh;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassGeneratorImpl
        extends ClassGenerator {

    public static Class generateClassImpl(String string, Modifiers modifiers, Class[] arrclass, Class class_, BSHBlock bSHBlock, boolean bl, CallStack callStack, Interpreter interpreter) throws EvalError {
        try {
            Capabilities.setAccessibility(true);
        } catch (Capabilities.Unavailable unavailable) {
            throw new EvalError("Defining classes currently requires reflective Accessibility.", bSHBlock, callStack);
        }
        NameSpace nameSpace = callStack.top();
        String string2 = nameSpace.getPackage();
        String string3 = nameSpace.isClass ? nameSpace.getName() + "$" + string : string;
        String string4 = string2 == null ? string3 : string2 + "." + string3;
        BSHClassManager bshClassManager = interpreter.getClassManager();
        bshClassManager.definingClass(string4);
        NameSpace nameSpace2 = new NameSpace(nameSpace, string3);
        nameSpace2.isClass = true;
        callStack.push(nameSpace2);
        bSHBlock.evalBlock(callStack, interpreter, true, ClassNodeFilter.CLASSCLASSES);
        Variable[] arrvariable = ClassGeneratorImpl.getDeclaredVariables(bSHBlock, callStack, interpreter, string2);
        DelayedEvalBshMethod[] arrdelayedEvalBshMethod = ClassGeneratorImpl.getDeclaredMethods(bSHBlock, callStack, interpreter, string2);
        ClassGeneratorUtil classGeneratorUtil = new ClassGeneratorUtil(modifiers, string3, string2, class_, arrclass, arrvariable, arrdelayedEvalBshMethod, nameSpace2, bl);
        byte[] arrby = classGeneratorUtil.generateClass();
        String string5 = System.getProperty("debugClasses");
        if (string5 != null) {
            try {
                try (FileOutputStream object = new FileOutputStream(string5 + "/" + string3 + ".class")) {
                    object.write(arrby);
                }
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        Class object1 = bshClassManager.defineClass(string4, arrby);
        nameSpace.importClass(string4.replace('$', '.'));
        try {
            nameSpace2.setLocalVariable("_bshInstanceInitializer", bSHBlock, false);
        } catch (UtilEvalError utilEvalError) {
            throw new InterpreterError("unable to init static: " + utilEvalError);
        }
        nameSpace2.setClassStatic(object1);
        bSHBlock.evalBlock(callStack, interpreter, true, ClassNodeFilter.CLASSSTATIC);
        callStack.pop();
        if (!object1.isInterface()) {
            String string6 = "_bshStatic" + string3;
            try {
                LHS lHS = Reflect.getLHSStaticField(object1, string6);
                lHS.assign(nameSpace2.getThis(interpreter), false);
            } catch (Exception exception) {
                throw new InterpreterError("Error in class gen setup: " + exception);
            }
        }
        bshClassManager.doneDefiningClass(string4);
        return object1;
    }

    static Variable[] getDeclaredVariables(BSHBlock bSHBlock, CallStack callStack, Interpreter interpreter, String string) {
        ArrayList<Variable> arrayList = new ArrayList<>();
        int n = 0;
        while (n < bSHBlock.jjtGetNumChildren()) {
            SimpleNode simpleNode = (SimpleNode) bSHBlock.jjtGetChild(n);
            if (simpleNode instanceof BSHTypedVariableDeclaration) {
                BSHTypedVariableDeclaration bSHTypedVariableDeclaration = (BSHTypedVariableDeclaration) simpleNode;
                Modifiers modifiers = bSHTypedVariableDeclaration.modifiers;
                String string2 = bSHTypedVariableDeclaration.getTypeDescriptor(callStack, interpreter, string);
                BSHVariableDeclarator[] arrbSHVariableDeclarator = bSHTypedVariableDeclaration.getDeclarators();
                int n2 = 0;
                while (n2 < arrbSHVariableDeclarator.length) {
                    String string3 = arrbSHVariableDeclarator[n2].name;
                    try {
                        Variable variable = new Variable(string3, string2, null, modifiers);
                        arrayList.add(variable);
                    } catch (UtilEvalError utilEvalError) {
                        // empty catch block
                    }
                    ++n2;
                }
            }
            ++n;
        }
        return arrayList.toArray(new Variable[arrayList.size()]);
    }

    static DelayedEvalBshMethod[] getDeclaredMethods(BSHBlock bSHBlock, CallStack callStack, Interpreter interpreter, String string) throws EvalError {
        ArrayList<DelayedEvalBshMethod> arrayList = new ArrayList<>();
        int n = 0;
        while (n < bSHBlock.jjtGetNumChildren()) {
            SimpleNode simpleNode = (SimpleNode) bSHBlock.jjtGetChild(n);
            if (simpleNode instanceof BSHMethodDeclaration) {
                BSHMethodDeclaration bSHMethodDeclaration = (BSHMethodDeclaration) simpleNode;
                bSHMethodDeclaration.insureNodesParsed();
                Modifiers modifiers = bSHMethodDeclaration.modifiers;
                String string2 = bSHMethodDeclaration.name;
                String string3 = bSHMethodDeclaration.getReturnTypeDescriptor(callStack, interpreter, string);
                BSHReturnType bSHReturnType = bSHMethodDeclaration.getReturnTypeNode();
                BSHFormalParameters bSHFormalParameters = bSHMethodDeclaration.paramsNode;
                String[] arrstring = bSHFormalParameters.getTypeDescriptors(callStack, interpreter, string);
                DelayedEvalBshMethod delayedEvalBshMethod = new DelayedEvalBshMethod(string2, string3, bSHReturnType, bSHMethodDeclaration.paramsNode.getParamNames(), arrstring, bSHFormalParameters, bSHMethodDeclaration.blockNode, null, modifiers, callStack, interpreter);
                arrayList.add(delayedEvalBshMethod);
            }
            ++n;
        }
        return arrayList.toArray(new DelayedEvalBshMethod[arrayList.size()]);
    }

    public static Object invokeSuperclassMethodImpl(BSHClassManager bshClassManager, Object object, String string, Object[] arrobject) throws UtilEvalError, ReflectError, InvocationTargetException {
        String string2 = "_bshSuper" + string;
        Class class_ = object.getClass();
        Method method = Reflect.resolveJavaMethod(bshClassManager, class_, string2, Types.getTypes(arrobject), false);
        if (method != null) {
            return Reflect.invokeMethod(method, object, arrobject);
        }
        Class class_2 = class_.getSuperclass();
        method = Reflect.resolveExpectedJavaMethod(bshClassManager, class_2, object, string, arrobject, false);
        return Reflect.invokeMethod(method, object, arrobject);
    }

    @Override
    public Class generateClass(String string, Modifiers modifiers, Class[] arrclass, Class class_, BSHBlock bSHBlock, boolean bl, CallStack callStack, Interpreter interpreter) throws EvalError {
        return ClassGeneratorImpl.generateClassImpl(string, modifiers, arrclass, class_, bSHBlock, bl, callStack, interpreter);
    }

    @Override
    public Object invokeSuperclassMethod(BSHClassManager bshClassManager, Object object, String string, Object[] arrobject) throws UtilEvalError, ReflectError, InvocationTargetException {
        return ClassGeneratorImpl.invokeSuperclassMethodImpl(bshClassManager, object, string, arrobject);
    }

    @Override
    public void setInstanceNameSpaceParent(Object object, String string, NameSpace nameSpace) {
        This this_ = ClassGeneratorUtil.getClassInstanceThis(object, string);
        this_.getNameSpace().setParent(nameSpace);
    }

    static class ClassNodeFilter
            implements BSHBlock.NodeFilter {

        public static final int STATIC = 0;
        public static final int INSTANCE = 1;
        public static final int CLASSES = 2;
        public static ClassNodeFilter CLASSSTATIC = new ClassNodeFilter(0);
        public static ClassNodeFilter CLASSINSTANCE = new ClassNodeFilter(1);
        public static ClassNodeFilter CLASSCLASSES = new ClassNodeFilter(2);
        int context;

        private ClassNodeFilter(int n) {
            this.context = n;
        }

        @Override
        public boolean isVisible(SimpleNode simpleNode) {
            if (this.context == 2) {
                return simpleNode instanceof BSHClassDeclaration;
            }
            if (simpleNode instanceof BSHClassDeclaration) {
                return false;
            }
            if (this.context == 0) {
                return this.isStatic(simpleNode);
            }
            if (this.context == 1) {
                return !this.isStatic(simpleNode);
            }
            return true;
        }

        boolean isStatic(SimpleNode simpleNode) {
            if (simpleNode instanceof BSHTypedVariableDeclaration) {
                return ((BSHTypedVariableDeclaration) simpleNode).modifiers != null && ((BSHTypedVariableDeclaration) simpleNode).modifiers.hasModifier("static");
            }
            if (simpleNode instanceof BSHMethodDeclaration) {
                return ((BSHMethodDeclaration) simpleNode).modifiers != null && ((BSHMethodDeclaration) simpleNode).modifiers.hasModifier("static");
            }
            if (simpleNode instanceof BSHBlock) {
                return false;
            }
            return false;
        }
    }

}
