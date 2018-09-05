package com.intellipix.steganography;
 
/**
 * Factory that creates instances of arbitrary objects.
 * 
 * {@link InstantiateSpecifiedClass} is an {@link InstanceFactory} that 
 * creates instances of specified class based on its no-argument constructor.
 *
 * @author mshnayderman
 * @since Jun 19, 2007
 */

public interface InstanceFactory {
    
    /**
     * Creates new instance of some class.
     */
    Object newInstance();
    
    public static class InstantiateSpecifiedClass implements InstanceFactory {

        private Class<?> classToInstantiate;

        public InstantiateSpecifiedClass(String fullyQualifiedClassName) throws ClassNotFoundException {            
            this(Class.forName(fullyQualifiedClassName));            
        }
        
        public InstantiateSpecifiedClass(Class<?> clazz) {
            classToInstantiate = clazz;
        }
                        
        /**
         * @see com.iex.tv.rcp.common.interfaces.InstanceFactory#newInstance()
         */
        @Override
        public Object newInstance() {
            try {
                return classToInstantiate.newInstance();
            }
            catch (InstantiationException except) {
                throw new RuntimeException(except);
            }
            catch (IllegalAccessException except) {
                throw new RuntimeException(except);
            }
        }
        
    }
}
